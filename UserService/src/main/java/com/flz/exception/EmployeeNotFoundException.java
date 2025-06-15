package com.flz.exception;

import java.io.Serial;

public final class EmployeeNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(Long id) {

        super("This Employee not found ID = " + id);
    }
}
