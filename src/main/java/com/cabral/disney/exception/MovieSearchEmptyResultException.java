package com.cabral.disney.exception;

public class MovieSearchEmptyResultException extends Exception {
    private static final long serialVersionUID = 4L;

    public MovieSearchEmptyResultException(String message) {
        super(message);
    }
}
