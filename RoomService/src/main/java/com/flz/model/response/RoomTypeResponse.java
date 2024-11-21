package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class RoomTypeResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer personCount;
    private Integer size;
    private String description;
    private List<AssetRoomTypeResponse> assets;
}
