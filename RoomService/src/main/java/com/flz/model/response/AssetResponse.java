package com.flz.model.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class AssetResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean isDefault;
}
