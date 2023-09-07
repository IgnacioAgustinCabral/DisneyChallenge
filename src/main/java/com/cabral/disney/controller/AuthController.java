package com.cabral.disney.controller;

import com.cabral.disney.exception.EmailAlreadyTakenException;
import com.cabral.disney.exception.UsernameAlreadyTakenException;
import com.cabral.disney.payload.request.RegisterRequest;
import com.cabral.disney.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody RegisterRequest request){
        try {
            return ResponseEntity.ok(this.authService.register(request));
        } catch (UsernameAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
