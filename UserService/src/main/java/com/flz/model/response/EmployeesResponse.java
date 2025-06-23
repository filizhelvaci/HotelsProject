package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeesResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String positionName;
    private String supervisorName;
    private String departmentName;

    private LocalDate startDate;
    private LocalDate endDate;
}
