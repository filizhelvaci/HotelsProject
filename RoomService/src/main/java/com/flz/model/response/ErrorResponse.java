package com.flz.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

}
