package com.flz.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExperienceCreateRequest {

    @NotNull
    @Range(min = 0, max = 10_000_000)
    private BigDecimal salary;

    @NotNull
    @Positive
    private Long positionId;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

}
