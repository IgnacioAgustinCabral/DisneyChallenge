package com.cabral.disney.exception;

public class MovieNotFoundException extends Exception {
    private static final long serialVersionUID = 3L;

    public MovieNotFoundException(String message) {
        super(message);
    }
}
