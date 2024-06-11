package com.flz.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {

    private final ErrorType type;

    public UserServiceException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public UserServiceException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

}