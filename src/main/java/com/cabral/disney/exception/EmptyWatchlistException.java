package com.cabral.disney.exception;

public class EmptyWatchlistException extends Exception {

    private static final long serialVersionUID = 20L;

    public EmptyWatchlistException(String message) {
        super(message);
    }
}
