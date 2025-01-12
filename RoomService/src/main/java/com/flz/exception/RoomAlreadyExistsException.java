package com.flz.exception;

import java.io.Serial;

public final class RoomAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RoomAlreadyExistsException(String message) {

        super(message + " --> this Room already exists ! ");
    }
}
