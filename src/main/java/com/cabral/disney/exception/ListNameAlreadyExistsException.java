package com.cabral.disney.exception;

public class ListNameAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 40L;

    public ListNameAlreadyExistsException(String message) {
        super(message);
    }
}
