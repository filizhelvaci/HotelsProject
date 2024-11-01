package com.flz.model.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
