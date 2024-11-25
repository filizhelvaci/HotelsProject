package com.flz.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime time = LocalDateTime.now();

    private Boolean isSuccess = false;

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

    public ErrorResponse(String message, String field, int code) {
        this.message = message;
        this.field = field;
        this.code = code;
    }
}
