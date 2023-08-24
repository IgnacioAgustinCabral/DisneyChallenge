package com.cabral.disney.exception;

public class PeliculaSearchEmptyResultException extends Exception {
    private static final long serialVersionUID = 4L;

    public PeliculaSearchEmptyResultException(String message) {
        super(message);
    }
}
