package com.myorganisation.gcafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Disable CSRF (Cross Site Request Forgery) to call ALL HTTP METHODS (GET, POST, PUT, PATCH, DELETE) without domain
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/api/dish", "/api/dish/**").permitAll()
                                .requestMatchers("/api", "/api/**").authenticated()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return httpSecurity.build();
    }
}
