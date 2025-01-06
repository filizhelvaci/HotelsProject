package com.flz.exception;

import java.io.Serial;

public abstract class AbstractNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1971229645656410132L;

    protected AbstractNotFoundException(String message) {

        super(" - NOT FOUND EXCEPTION - " + message);
    }
}
