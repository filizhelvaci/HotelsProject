package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RoomTypeAvailabilityResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer personCount;
    private Integer size;
    private Boolean isAvailability;

}
