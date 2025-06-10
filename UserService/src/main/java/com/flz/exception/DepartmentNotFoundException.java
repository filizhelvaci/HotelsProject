package com.flz.exception;

import java.io.Serial;

public final class DepartmentNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = -5692997481627512574L;

    public DepartmentNotFoundException(Long id) {

        super("This Department not found ID = " + id);
    }
}
