package com.flz.exception;

import java.io.Serial;

public final class RoomTypeAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = -6332098600699563568L;

    public RoomTypeAlreadyExistsException(String message) {

        super(message + "  -> this Room Type already exists ! ");
    }
}
