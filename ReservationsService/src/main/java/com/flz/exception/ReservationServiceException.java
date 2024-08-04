package com.flz.exception;

import lombok.Getter;

@Getter
public class ReservationServiceException extends RuntimeException {

    private final ErrorType type;

    public ReservationServiceException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public ReservationServiceException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

}