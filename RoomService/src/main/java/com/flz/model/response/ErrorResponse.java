package com.flz.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private final String message;

    private String field;

    private int code;

    @Builder.Default
    private Boolean isSuccess = false;

    public ErrorResponse(String message) {
        this.message = message;
        this.isSuccess = Boolean.FALSE;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(LocalDateTime dateTime, Boolean isSuccess, String message) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.timestamp = dateTime;
    }

    public ErrorResponse(String message, String field) {
        this.message = message;
        this.field = field;
        isSuccess = Boolean.FALSE;
    }

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
        isSuccess = Boolean.FALSE;
    }

    public ErrorResponse(String message, String field, int code) {
        this.message = message;
        this.field = field;
        this.code = code;
        isSuccess = Boolean.FALSE;
    }
}
