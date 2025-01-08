package com.flz.exception;

import java.io.Serial;

public final class RoomNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RoomNotFoundException(Long id) {

        super("This Room not found ID: " + id);
    }


}
