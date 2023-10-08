package com.cabral.disney.exception;

public class CharacterSearchEmptyResultException extends Exception {
    private static final long serialVersionUID = 1L;

    public CharacterSearchEmptyResultException(String message) {
        super(message);
    }
}
