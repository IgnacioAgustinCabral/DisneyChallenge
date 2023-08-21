package com.cabral.disney.exception;

public class PersonajeSearchEmptyResultException extends Exception {
    private static final long serialVersionUID = 1L;

    public PersonajeSearchEmptyResultException(String message) {
        super(message);
    }
}
