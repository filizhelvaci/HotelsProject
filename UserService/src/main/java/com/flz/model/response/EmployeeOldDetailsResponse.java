package com.flz.model.response;

import com.flz.model.EmployeeOld;
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
public class EmployeeOldDetailsResponse {

    private EmployeeOld employeeOld;

    private List<EmployeeOldExperienceResponse> experiences;

}
