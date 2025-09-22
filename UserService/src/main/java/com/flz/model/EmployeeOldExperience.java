package com.flz.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmployeeOldExperience extends BaseDomainModel {

    private Long id;
    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;

    private Position position;
    private EmployeeOld employeeOld;

}
