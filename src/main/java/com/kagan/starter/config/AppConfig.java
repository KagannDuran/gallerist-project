package com.kagan.starter.config;

import com.kagan.starter.entity.AppUser;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration  // Spring'e bu sınıfın bir konfigürasyon sınıfı olduğunu belirtir. Yani, Spring Container'ı içinde bazı bean'lerin tanımlanacağı yerdir.
public class AppConfig {

    private final UserRepository userRepository;  // UserRepository, kullanıcı bilgilerini veritabanından alacak olan repository sınıfı.

    @Autowired  // Spring, sınıfa otomatik olarak UserRepository'nin bir örneğini enjekte eder.
    public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;  // Constructor üzerinden userRepository bağımlılığını alır ve sınıfın field'ına atar.
    }

    // UserDetailsService Bean'ini tanımlar. Bu sınıf, Spring Security'nin kullanıcı bilgilerini yüklemesi için kullanılır.
    @Bean  // Bu anotasyon, bu method'un bir Spring Bean'i olduğunu belirtir.
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {  // Spring Security için kullanıcıyı yükleyecek özel bir UserDetailsService implementasyonu.
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Kullanıcı adıyla kullanıcıyı veritabanından arar.
                Optional<AppUser> optional = userRepository.findByUsername(username);
                if (optional.isEmpty()) {  // Eğer kullanıcı bulunamazsa
                    throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, username));  // Özel bir hata fırlatılır.
                }
                return optional.get();  // Kullanıcı bulunduysa, UserDetails olarak geri döndürülür.
            }
        };
    }

    // DaoAuthenticationProvider Bean'ini tanımlar. Bu sınıf, Spring Security'nin kimlik doğrulama sağlayıcısıdır.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();  // DaoAuthenticationProvider nesnesi oluşturuluyor.
        provider.setUserDetailsService(userDetailsService());  // Kullanıcıyı yükleyecek UserDetailsService ayarlanıyor.
        provider.setPasswordEncoder(passwordEncoder());  // Şifreyi doğrulamak için kullanılacak encoder (şifreleme algoritması) ayarlanıyor.
        return provider;  // DaoAuthenticationProvider döndürülür.
    }

    // AuthenticationManager Bean'ini tanımlar. AuthenticationManager, kimlik doğrulama işlemi için Spring Security'nin merkezi bileşenidir.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();  // AuthenticationConfiguration'dan AuthenticationManager döndürülür.
    }

    // BCryptPasswordEncoder Bean'ini tanımlar. Bu sınıf, şifrelerin güvenli bir şekilde şifrelenmesini sağlar.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Şifreleme işlemi için BCryptPasswordEncoder nesnesi döndürülür.
    }
}

