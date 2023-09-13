package com.cabral.disney.service.impl;

import com.cabral.disney.entity.User;
import com.cabral.disney.exception.EmailAlreadyTakenException;
import com.cabral.disney.exception.UsernameAlreadyTakenException;
import com.cabral.disney.mapper.UserMapper;
import com.cabral.disney.payload.request.LoginRequest;
import com.cabral.disney.payload.request.RegisterRequest;
import com.cabral.disney.payload.response.AuthResponse;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private EmailService emailService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) throws UsernameAlreadyTakenException, EmailAlreadyTakenException, IOException {
        if (this.userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyTakenException("Username already in use.");
        } else if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyTakenException("Email already in use");
        } else {
            User newUser = UserMapper.mapToEntity(request);
            this.userRepository.save(newUser);
//            EmailService.send(request.getEmail());
            emailService.sendEmail(request.getEmail());
            return AuthResponse.builder().message(jwtService.getToken(newUser)).build();
        }
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .message(token)
                .build();
    }
}
