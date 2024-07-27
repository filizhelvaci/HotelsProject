package com.flz.exception;

import lombok.Getter;

@Getter
public class RepositoryServiceException extends RuntimeException {

    private final ErrorType type;

    public RepositoryServiceException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public RepositoryServiceException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

}