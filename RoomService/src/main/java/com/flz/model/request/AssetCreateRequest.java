package com.flz.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetCreateRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Range(min = 0, max = 10000)
    private BigDecimal price;

    @NotNull
    private Boolean isDefault;


}
