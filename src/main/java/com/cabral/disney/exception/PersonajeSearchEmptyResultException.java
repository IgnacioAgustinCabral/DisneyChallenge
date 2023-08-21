package com.cabral.disney.exception;

public class PersonajeSearchEmptyResultException extends Exception {
    private static final long serialVerisionUID = 1;

    public PersonajeSearchEmptyResultException(String message) {
        super(message);
    }
}
