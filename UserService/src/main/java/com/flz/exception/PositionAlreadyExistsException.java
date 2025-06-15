package com.flz.exception;

import java.io.Serial;

public final class PositionAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PositionAlreadyExistsException(String message) {

        super(message + " -> this position already exists ! ");
    }

}
