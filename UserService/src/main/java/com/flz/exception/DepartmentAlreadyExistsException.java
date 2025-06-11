package com.flz.exception;

import java.io.Serial;

public final class DepartmentAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DepartmentAlreadyExistsException(String message) {

        super(message + " -> this department already exists ! ");
    }

}
