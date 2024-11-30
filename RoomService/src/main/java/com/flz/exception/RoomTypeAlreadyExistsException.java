package com.flz.exception;

import java.io.Serial;

public final class RoomTypeAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RoomTypeAlreadyExistsException(String message) {

        super("Room Type already exists ! ");
    }
}
