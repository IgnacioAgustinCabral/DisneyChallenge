package com.cabral.disney.exception;

public class CharacterNotFoundException extends Exception {
    private static final long serialVersionUID = 2L;

    public CharacterNotFoundException(String message) {
        super(message);
    }
}
