package com.flz.exception;

import java.io.Serial;

public final class EmployeeAlreadyManagerException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeAlreadyManagerException(String message) {

        super("This employee is a manager so please first update the " + message +"'s manager" );
    }
}
