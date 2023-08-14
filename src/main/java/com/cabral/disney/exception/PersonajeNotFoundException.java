package com.cabral.disney.exception;

public class PersonajeNotFoundException extends Exception {
    private static final long serialVerisionUID = 1;

    public PersonajeNotFoundException(String message) {
        super(message);
    }
}
