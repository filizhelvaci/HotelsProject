package com.flz.exception;

import java.io.Serial;

public final class DepartmentAlreadyDeletedException extends AbstractConflictException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DepartmentAlreadyDeletedException(Long id) {

        super("Department with ID " + id + " is already deleted.");
    }
}
