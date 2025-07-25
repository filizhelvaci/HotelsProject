package com.flz.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExperienceCreateRequest {

    @NotNull
    @Range(min = 0, max = 10_000_000)
    private BigDecimal salary;

    @NotNull
    private Long positionId;

    @NotNull
    private Long supervisorId;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

}
