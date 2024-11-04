package com.flz.exception;

public abstract class AbstractNotFoundException extends RuntimeException {

    protected AbstractNotFoundException(String message) {
        super(message);
    }
}
