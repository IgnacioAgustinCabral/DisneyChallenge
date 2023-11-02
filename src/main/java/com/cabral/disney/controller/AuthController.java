package com.cabral.disney.controller;

import com.cabral.disney.exception.EmailAlreadyTakenException;
import com.cabral.disney.exception.UsernameAlreadyTakenException;
import com.cabral.disney.payload.request.LoginRequest;
import com.cabral.disney.payload.request.RegisterRequest;
import com.cabral.disney.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestPart("registerRequest") RegisterRequest request,
                                      @RequestPart("profile_picture") MultipartFile profilePicture) throws EmailAlreadyTakenException,IOException, UsernameAlreadyTakenException {
        return ResponseEntity.ok(this.authService.register(request, profilePicture));
    }
}
