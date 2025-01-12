package com.flz.model.request;

import com.flz.model.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class RoomCreateRequest {

    @NotNull
    @Range(min = 1, max = 10000)
    private Integer number;

    @NotNull
    @Range(min = 0, max = 100)
    private Integer floor;

    @NotNull
    private RoomStatus status;

    @NotNull
    private Long roomTypeId;

}
