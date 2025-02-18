package com.flz.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeCreateRequest {

    @NotBlank
    @Size(min = 2, max = 99)
    private String name;

    @NotNull
    @Range(min = 0, max = 10_000_000)
    private BigDecimal price;

    @NotNull
    @Range(min = 0, max = 100)
    private Integer personCount;

    @NotNull
    @Range(min = 0, max = 1000)
    private Integer size;

    @NotBlank
    @Size(min = 1, max = 1000)
    private String description;

    @NotEmpty
    private List<Long> assetIds;
}
