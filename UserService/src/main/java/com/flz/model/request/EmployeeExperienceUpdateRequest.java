package com.flz.model.request;

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
public class EmployeeExperienceUpdateRequest {

    @Range(min = 0, max = 10_000_000)
    private BigDecimal salary;

    private Long supervisorId;

}
