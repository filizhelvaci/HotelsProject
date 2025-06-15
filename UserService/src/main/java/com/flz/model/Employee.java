package com.flz.model;

import com.flz.model.enums.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseDomainModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private Gender gender;
    private String nationality;

    private List<EmployeeExperience> experiences;

}
