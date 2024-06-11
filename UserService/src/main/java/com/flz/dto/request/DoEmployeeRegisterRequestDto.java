package com.flz.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder // bir sınıfın kolayca nesne oluşturmasını sağlar
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
public class DoEmployeeRegisterRequestDto {

    private String insideNumber;
    private String birthDate;
    private String IDnumber;
    private Date contractPeriod;
    private String graduationStatus;
    private Date graduationYear;

    private String name;
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String password;
    private String rePassword;
}
