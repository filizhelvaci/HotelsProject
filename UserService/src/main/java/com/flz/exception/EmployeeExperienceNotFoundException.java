package com.flz.exception;

import java.io.Serial;

public final class EmployeeExperienceNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeExperienceNotFoundException(Long id) {

        super("This EmployeeExperience not found ID = " + id);
    }
}
