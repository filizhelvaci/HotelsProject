package com.flz.model.request;

import com.flz.model.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateRequest {

    @NotNull
    @Range(min = 1, max = 10000)
    private Integer number;

    @NotNull
    @Range(min = 0, max = 100)
    private Integer floor;

    @NotNull
    private RoomStatus status;

    @NotNull
    @Range(min = 0, max = 500)
    private Long roomTypeId;

}
