package com.cabral.disney.exception;

public class PeliculaSearchEmptyResultException extends Throwable {
    private static final long serialVersionUID = 4L;

    public PeliculaSearchEmptyResultException(String message) {
        super(message);
    }
}
