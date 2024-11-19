package com.flz.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class HotelResponse<T> {
    /**
     * The timestamp indicating when the response was created.
     */
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    /**
     * A unique code identifying the response.
     */
    @Builder.Default
    private String code = UUID.randomUUID().toString();

    /**
     * Indicates whether the API request was successful or not.
     */
    private Boolean isSuccess;

    /**
     * The response object.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;


    /**
     * A success response with no content.
     */
    public static <T> HotelResponse<T> success() {
        return HotelResponse.<T>builder().isSuccess(true).build();
    }

    /**
     * Creates a success response with the specified response object.
     *
     * @param <T>      The type of the response object.
     * @param response The response object.
     * @return An instance of {@link HotelResponse} representing a successful response.
     */
    public static <T> HotelResponse<T> successOf(final T response) {
        return HotelResponse.<T>builder().isSuccess(true).response(response).build();
    }

}
