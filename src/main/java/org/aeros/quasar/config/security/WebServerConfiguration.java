package org.aeros.quasar.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebServerConfiguration {

    private final String[] ALLOWED_ENDPOINTS = {
            "/api/v1/users/register",
            "/api/v1/clients/register",
            "/actuator/**"
    };


    @Bean
    @Order(0)
    SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.ignoringRequestMatchers(ALLOWED_ENDPOINTS));
        http.formLogin(c -> c.failureForwardUrl("/login"))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(ALLOWED_ENDPOINTS).permitAll();
                    auth.anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
