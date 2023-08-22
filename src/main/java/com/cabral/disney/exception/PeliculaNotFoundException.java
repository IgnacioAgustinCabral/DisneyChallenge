package com.cabral.disney.exception;

public class PeliculaNotFoundException extends Exception {
    private static final long serialVersionUID = 3L;

    public PeliculaNotFoundException(String message) {
        super(message);
    }
}
