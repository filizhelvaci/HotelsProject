package com.flz.exception;

import java.io.Serial;

public final class PositionAlreadyDeletedException extends AbstractConflictException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PositionAlreadyDeletedException(Long id) {

        super("Position with id " + id + " is already deleted.");
    }

}
