package com.kagan.starter.jwt;

import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Spring'e bu sınıfın bir bileşen olduğunu ve otomatik olarak DI (dependency injection) ile enjekte edilebileceğini belirtir.
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;  // JWT işlemleri için kullanılan servis.
    private final UserDetailsService userDetailsService;  // Kullanıcı bilgilerini almak için kullanılan servis.

    @Autowired  // Spring'in bu constructor'ı kullanarak bağımlılıkları enjekte etmesini sağlar.
    public JWTAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // `doFilterInternal` metodu, gelen her istekte bir kere çalışır ve JWT doğrulama işlemini yapar.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Authorization başlığını alır.
        String header = request.getHeader("Authorization");

        // Eğer Authorization başlığı yoksa, bir işlem yapmadan gelen isteği bir sonraki filtreye göndeririz.
        if(header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token;
        String userName;

        // Authorization başlığından "Bearer " kısmını çıkararak token'ı alırız (Token, başlıkta "Bearer <token>" şeklinde gelir).
        token = header.substring(7);

        try {
            // JWT'den kullanıcı adını çıkarırız.
            userName = jwtService.getUserNameByToken(token);

            // Eğer kullanıcı adı varsa ve Spring Security bağlamında zaten bir kimlik doğrulama yoksa, token'ı geçerli kabul ederiz.
            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Kullanıcıyı veritabanından alırız.
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                // Eğer kullanıcı var ve token geçerli ise, kullanıcı doğrulama işlemi yapılır.
                if(userDetails != null && jwtService.isTokenValid(token)) {
                    // Authentication objesi oluştururuz, bu obje Spring Security'nin kimlik doğrulama sistemine kimlik bilgilerini verecektir.
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());

                    // Kullanıcı bilgilerini authentication objesine ekleriz.
                    authenticationToken.setDetails(userDetails);

                    // Spring Security bağlamına kimlik doğrulamayı ekleriz.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (ExpiredJwtException ex) {
            // Token süresi dolmuşsa, özel bir hata fırlatırız.
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPORED, ex.getMessage()));
        } catch (Exception e) {
            // Diğer hatalar için genel bir hata fırlatırız.
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }

        // Token doğrulaması yapılmışsa, bir sonraki filtreye geçilir.
        filterChain.doFilter(request, response);
    }
}
