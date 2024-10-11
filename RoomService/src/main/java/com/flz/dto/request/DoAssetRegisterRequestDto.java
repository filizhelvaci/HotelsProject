package com.flz.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DoAssetRegisterRequestDto {

    private String name;
    private BigDecimal price;
    private String isDefault;
}
