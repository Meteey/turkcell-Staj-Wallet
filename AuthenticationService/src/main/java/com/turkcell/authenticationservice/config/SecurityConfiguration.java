package com.turkcell.authenticationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF'yi devre dışı bırak
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // /auth/** herkese açık
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
