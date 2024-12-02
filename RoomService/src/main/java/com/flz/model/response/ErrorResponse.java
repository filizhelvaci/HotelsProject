package com.flz.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    @Builder.Default
    private Boolean isSuccess = false;

    private final String message;

    private String field;

    private int code;
}
