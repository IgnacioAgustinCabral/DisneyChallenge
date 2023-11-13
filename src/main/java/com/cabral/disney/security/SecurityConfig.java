package com.cabral.disney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationProvider authProvider;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .mvcMatchers(HttpMethod.POST, "/characters/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.PUT, "/characters/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.DELETE, "/characters/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.POST, "/movies/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.PUT, "/movies/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.POST, "/user/**").authenticated()
                        .mvcMatchers(HttpMethod.DELETE, "/user/**").authenticated()
                        .mvcMatchers(HttpMethod.PUT, "/user/**").authenticated()
                        .anyRequest().permitAll())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}