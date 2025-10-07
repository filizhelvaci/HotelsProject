package com.flz.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmployeeExperience extends BaseDomainModel {

    private Long id;
    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;

    private Position position;
    private Employee employee;


    public void closeExperience(LocalDate nextExperienceStartDate) {

        LocalDate newEndDate = nextExperienceStartDate.minusDays(1);

        this.setEndDate(newEndDate);
        this.setUpdatedAt(LocalDateTime.now());
        this.setUpdatedBy("System");
    }

}