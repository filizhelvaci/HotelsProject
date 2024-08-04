package com.flz.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Year;
import java.time.YearMonth;
import java.util.Date;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoEmployeeRegisterRequestDto {

    private String insideNumber;
    private String birthDate;
    private String IDnumber;
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
