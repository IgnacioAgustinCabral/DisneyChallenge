package com.cabral.disney.exception;

public class EmailAlreadyTakenException extends Throwable {
    private static final long serialVersionUID = 9L;

    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
