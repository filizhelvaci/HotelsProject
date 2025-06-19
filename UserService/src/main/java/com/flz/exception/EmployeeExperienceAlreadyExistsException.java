package com.flz.exception;

import java.io.Serial;

public final class EmployeeExperienceAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeExperienceAlreadyExistsException(Long id) {

        super(" This experience for " + id + " employee already exists ! ");

    }
}
