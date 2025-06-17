package com.flz.exception;

import java.io.Serial;

public final class EmployeeAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeAlreadyExistsException(String message) {

        super(message + " -> this Employee already exists ! ");
    }
}
