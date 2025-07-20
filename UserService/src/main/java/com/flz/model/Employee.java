package com.flz.model;

import com.flz.model.enums.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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

    public void update(String firstName, String lastName, String identityNumber, String email, String phoneNumber, String address, LocalDate birthDate, Gender gender, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNumber = identityNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.nationality = nationality;
    }

}
