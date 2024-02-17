package com.cabral.disney.security;

import com.cabral.disney.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private MyUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authProvider) throws Exception {
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
                        .mvcMatchers(HttpMethod.POST, "/watchlist/{movieId}/add-to-watchlist").hasRole("USER")
                        .mvcMatchers(HttpMethod.DELETE,"/watchlist/{movieId}/remove").hasRole("USER")
                        .anyRequest().permitAll())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtservice, MyUserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        return new JwtAuthenticationFilter(jwtservice, userDetailsService, resolver);
    }
}