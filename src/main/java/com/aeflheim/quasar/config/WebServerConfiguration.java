package com.aeflheim.quasar.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class WebServerConfiguration {

    private final CorsCustomizer corsCustomizer;

  @Bean
  @Order(2)
  SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
      corsCustomizer.customize(http);
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
