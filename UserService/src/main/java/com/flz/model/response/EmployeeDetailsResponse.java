package com.flz.model.response;

import com.flz.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsResponse {

    private Employee employee;

    private List<EmployeeExperienceDetailResponse> experiences;

}
