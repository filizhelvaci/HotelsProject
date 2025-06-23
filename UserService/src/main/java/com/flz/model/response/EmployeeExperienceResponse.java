package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeExperienceResponse {

    private Long id;
    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long positionId;
    private String positionName;

    private Long supervisorId;
    private String supervisorName;

}
