package com.flz.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class HotelResponse<T> {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private Boolean isSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    public static <T> HotelResponse<T> success() {
        return HotelResponse.<T>builder().isSuccess(true).build();
    }

    public static <T> HotelResponse<T> successOf(final T response) {
        return HotelResponse.<T>builder()
                .isSuccess(true)
                .response(response).build();
    }


}
