package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeOldExperienceResponse {

    private Long id;
    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;

    private String positionName;
    private String departmentName;
    private String managerName;

}
