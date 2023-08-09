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


    @Bean
  @Order(2)
  SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(c -> c.failureForwardUrl("/login"))
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/actuator/**").permitAll();
          auth.anyRequest().authenticated();
        });

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
