package com.flz.exception;

import java.io.Serial;

public abstract class AbstractConflictException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    protected AbstractConflictException(String message) {

        super(message);
    }
}
