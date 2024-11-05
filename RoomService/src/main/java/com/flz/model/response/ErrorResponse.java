package com.flz.model.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private String field;
    private int code;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
