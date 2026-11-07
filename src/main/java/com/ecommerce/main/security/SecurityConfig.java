package com.ecommerce.main.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {

    @Bean


    public SecurityFilterChain securityFilterChain(JwtAuthenticationFilter jwtAuthenticationFilter, HttpSecurity http, JwtAuthenticationEntryPoint entryPoint,
                                                   JwtAccessDeniedHandler accessDeniedHandler) throws Exception {
        return http.formLogin(form -> form.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())   // uses the CorsConfigurationSource bean
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll().requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                ).permitAll().requestMatchers("api/v1/book/**").permitAll().anyRequest().authenticated())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}