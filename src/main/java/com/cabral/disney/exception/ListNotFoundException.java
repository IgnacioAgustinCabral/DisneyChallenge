package com.cabral.disney.exception;

public class ListNotFoundException extends Exception {
    private static final long serialVersionUID = 12L;

    public ListNotFoundException(String message) {
        super(message);
    }
}
