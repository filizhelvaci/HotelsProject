package com.flz.exception;

import org.springframework.dao.DataAccessException;

import java.io.Serial;

public class DataAccessConflictException extends DataAccessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DataAccessConflictException(String msg) {
        super(msg);
    }
}
