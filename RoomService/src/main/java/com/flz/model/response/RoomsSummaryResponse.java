package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RoomsSummaryResponse {

    private Long id;
    private Integer number;

}
