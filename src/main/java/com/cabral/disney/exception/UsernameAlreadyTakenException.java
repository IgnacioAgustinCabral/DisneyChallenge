package com.cabral.disney.exception;

public class UsernameAlreadyTakenException extends Exception {
    private static final long serialVersionUID = 8L;

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
