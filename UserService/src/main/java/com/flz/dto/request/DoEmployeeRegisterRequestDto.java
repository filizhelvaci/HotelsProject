package com.flz.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DoEmployeeRegisterRequestDto {

    private String insideNumber;
    private String birthDate;
    private String idNumber;
    private int contractPeriod;
    private String graduationStatus;
    private String graduationYear;
    private String name;
    private String lastname;
    @Email
    private String email;
    private String phoneNumber;
    private String password;
    private String rePassword;
}
