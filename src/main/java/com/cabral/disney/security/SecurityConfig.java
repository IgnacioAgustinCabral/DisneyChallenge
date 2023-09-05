package com.cabral.disney.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll() // Require authentication for /personajes and its subpaths
//                .anyRequest().permitAll() // Allow unauthenticated access to all other endpoints
                .and()
                .formLogin()
                .permitAll() // Use Spring Security's default login page
                .and()
                .logout()
                .permitAll();
    }

    // Other security configurations, authentication providers, etc.
}