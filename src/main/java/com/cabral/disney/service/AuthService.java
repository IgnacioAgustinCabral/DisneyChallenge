package com.cabral.disney.service;

import com.cabral.disney.exception.EmailAlreadyTakenException;
import com.cabral.disney.exception.UsernameAlreadyTakenException;
import com.cabral.disney.payload.request.LoginRequest;
import com.cabral.disney.payload.request.RegisterRequest;
import com.cabral.disney.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request) throws UsernameAlreadyTakenException, EmailAlreadyTakenException;

    AuthResponse login(LoginRequest request);
}
