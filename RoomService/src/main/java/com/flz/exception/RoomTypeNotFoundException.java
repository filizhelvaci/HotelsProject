package com.flz.exception;

import java.io.Serial;

public final class RoomTypeNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1458755075792352182L;

    public RoomTypeNotFoundException(Long id) {

        super("This Room Type not found ID = " + id);
    }
}
