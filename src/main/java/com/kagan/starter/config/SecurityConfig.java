package com.kagan.starter.config;

import com.kagan.starter.handler.AuthEntryPoint;
import com.kagan.starter.jwt.JWTAuthenticationFilter;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Bu sınıf Spring Security yapılandırmasını tanımlar.
@Configuration
@EnableWebSecurity  // Web güvenliğini etkinleştirir.
public class SecurityConfig {

    // Güvenliğe açık bırakılacak endpoint sabitleri
    private static final String REGISTER = "/register";
    private static final String AUTHENTICATE = "/authenticate";
    private static final String REFRESH_TOKEN = "/refreshToken";



    // Gerekli bağımlılıklar: authenticationProvider ve JWT filtresi
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPoint authEntryPoint;

    // Bağımlılık enjeksiyonu (constructor injection)
    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider, JWTAuthenticationFilter jwtAuthenticationFilter, AuthEntryPoint authEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authEntryPoint = authEntryPoint;
    }

    // Güvenlik filtresi zincirini yapılandıran bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF (Cross Site Request Forgery) korumasını devre dışı bırakır.
                // JWT tabanlı sistemlerde gereksizdir çünkü state yoktur.
                .csrf(csrf -> csrf.disable())

                // Yetkilendirme kurallarını tanımlar
                .authorizeHttpRequests(request ->
                        request
                                // Belirtilen endpoint'lere kimlik doğrulama gerekmez
                                .requestMatchers(REGISTER, AUTHENTICATE, REFRESH_TOKEN).permitAll()
                                // Diğer tüm endpoint'ler için kimlik doğrulama zorunludur
                                .anyRequest().authenticated()
                        // Kimlik doğrulama hatalarında özel giriş noktası olarak 'authEntryPoint' kullanılır
                ) .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint))

                // Oturum oluşturma politikasını STATELESS yapar
                // Çünkü her istekte JWT ile doğrulama yapılır, oturum tutulmaz
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // AuthenticationProvider yapılandırması
                .authenticationProvider(authenticationProvider)

                // JWT doğrulama filtresini UsernamePasswordAuthenticationFilter'dan önce çalıştırır
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // SecurityFilterChain bean'i döndürülür
        return http.build();
    }
}
