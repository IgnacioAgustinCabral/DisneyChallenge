package com.cabral.disney.service.impl;

import com.cabral.disney.exception.EmailAlreadyTakenException;
import com.cabral.disney.exception.UsernameAlreadyTakenException;
import com.cabral.disney.mapper.UserMapper;
import com.cabral.disney.payload.request.RegisterRequest;
import com.cabral.disney.payload.response.AuthResponse;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse register(RegisterRequest request) throws UsernameAlreadyTakenException, EmailAlreadyTakenException {
        if (this.userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyTakenException("Username already in use.");
        } else if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyTakenException("Email already in use");
        } else {
            this.userRepository.save(UserMapper.mapToEntity(request));
            return UserMapper.mapToDTO("User registered successfully.");
        }
    }
}
