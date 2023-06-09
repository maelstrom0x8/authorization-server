package com.aeflheim.quasar.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsCustomizer {

    private static final String[] ALLOWED_ENDPOINTS = {
            "http://127.0.0.1:3000"
    };


    public void customize(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = r -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowedOrigins(List.of(ALLOWED_ENDPOINTS));
                cc.setAllowedHeaders(List.of("*"));
                cc.setAllowedMethods(List.of("*"));
                return cc;
            };

            c.configurationSource(source);
        });
    }
}
