package com.raffasdev.registrapi.config;

import com.raffasdev.registrapi.infrastructure.security.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder(PasswordEncoder encoder) {
        return new BCryptPasswordEncoder(encoder);
    }

}
