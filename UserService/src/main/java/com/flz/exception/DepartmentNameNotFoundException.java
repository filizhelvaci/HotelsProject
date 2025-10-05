package com.flz.exception;

import java.io.Serial;

public final class DepartmentNameNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DepartmentNameNotFoundException(String message) {

        super(message + " department not found");
    }

}
