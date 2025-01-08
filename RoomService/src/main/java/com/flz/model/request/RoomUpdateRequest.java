package com.flz.model.request;

import com.flz.model.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class RoomUpdateRequest {

    @NotNull
    @Range(min = 1, max = 10000)
    private Integer number;

    @NotNull
    @Range(min = 0, max = 100)
    private Integer floor;

    @NotNull
    private RoomStatus status;

}
