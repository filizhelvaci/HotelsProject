package com.flz.exception;

import java.io.Serial;

public abstract class AbstractAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    protected AbstractAlreadyExistsException(String message) {

        super(" - CONFLICT ERROR - " + message);
    }
}
