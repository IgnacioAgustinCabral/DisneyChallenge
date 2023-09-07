package com.cabral.disney.security;

import com.cabral.disney.service.impl.JPAUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JPAUserDetailsServiceImpl myUserDetailsService;
    @Autowired
    public SecurityConfig(JPAUserDetailsServiceImpl myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .mvcMatchers("/personajes/**").authenticated()
                        .anyRequest().permitAll())
                .userDetailsService(myUserDetailsService)
                .httpBasic(withDefaults())
                .build();
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
//                .antMatchers("/").permitAll() // Require authentication for /personajes and its subpaths
                .anyRequest().permitAll() // Allow unauthenticated access to all other endpoints
                .and()
                .formLogin()
                .permitAll() // Use Spring Security's default login page
                .and()
                .logout()
                .permitAll();
    }*/

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}