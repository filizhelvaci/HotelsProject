package com.flz.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DoCustomerRegisterRequestDto {

    private String IDnumber;
    private String nationality;
    private String birthDate;
    private String name;
    private String lastname;
    @Email
    private String email;
    private String phoneNumber;
    private String password;
    private String rePassword;
}
