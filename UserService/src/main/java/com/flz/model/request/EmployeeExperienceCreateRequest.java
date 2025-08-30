package com.flz.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExperienceCreateRequest {

    @NotNull
    @Positive
    private BigDecimal salary;

    @NotNull
    @Positive
    private Long positionId;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

}
