package com.flz.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime time;

    private Boolean isSuccess;

    private final String message;

    private String field;

    private int code;
}
