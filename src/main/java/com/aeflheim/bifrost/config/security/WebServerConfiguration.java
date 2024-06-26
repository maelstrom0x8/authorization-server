/*
 * Copyright (C) 2024 Emmanuel Godwin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aeflheim.bifrost.config.security;

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
        "/api/v1/users/register", "/api/v1/clients/register", "/actuator/**"
    };

    @Bean
    @Order(0)
    SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.ignoringRequestMatchers(ALLOWED_ENDPOINTS));
        http.formLogin(c -> c.failureForwardUrl("/login"))
                .authorizeHttpRequests(
                        auth -> {
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
