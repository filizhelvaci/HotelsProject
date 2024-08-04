package com.flz.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

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
