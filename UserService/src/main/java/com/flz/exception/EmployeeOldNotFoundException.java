package com.flz.exception;

import java.io.Serial;

public final class EmployeeOldNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeOldNotFoundException(Long id) {

        super("This Employee not found ID = " + id);
    }
}
