package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    @Builder.Default
    private Boolean isSuccess = false;

    private String message;

    private String field;

    private int code;
}
