package com.flz.exception;

import java.io.Serial;

public final class PositionNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PositionNotFoundException(Long id) {

        super("This position not found ID = " + id);
    }
}
