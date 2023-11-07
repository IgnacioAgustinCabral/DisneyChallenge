package com.cabral.disney.exception;

public class UsernameNotFoundException extends Exception {
    private static final long serialVersionUID = 16L;

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
