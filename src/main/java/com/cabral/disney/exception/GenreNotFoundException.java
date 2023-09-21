package com.cabral.disney.exception;

public class GenreNotFoundException extends Exception {
    private static final long serialVersionUID = 12L;
    public GenreNotFoundException(String message) {
        super(message);
    }
}
