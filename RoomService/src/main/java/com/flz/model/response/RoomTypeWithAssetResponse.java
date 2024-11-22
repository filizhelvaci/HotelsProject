package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
public class RoomTypeWithAssetResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer personCount;
    private Integer size;
    private String description;
    private List<String> assets;

}
