package com.flz.exception;

import java.io.Serial;

public class RoomTypeNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1458755075792352182L;

    public RoomTypeNotFoundException(Long id) {
        super("Room Type not found ID = " + id);
    }
}
