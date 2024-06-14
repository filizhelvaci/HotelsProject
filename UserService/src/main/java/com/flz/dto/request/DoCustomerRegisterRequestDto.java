package com.flz.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // bir sınıfın kolayca nesne oluşturmasını sağlar
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
public class DoCustomerRegisterRequestDto {

    private Long id;
    private String IDnumber;
    private String nationality;
    private String birthDate;
    private String name;
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String password;
    private String rePassword;
}
