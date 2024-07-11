package com.flz.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // bir sınıfın kolayca nesne oluşturmasını sağlar
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
public class DoRegisterRequestDto {

        private String name;
        private String lastName;

        // @Email kısmı burdada yapılabilir.
        @Email
        private String email;

        private String phoneNumber;

        // private static final String PASSWORD_PATTERN =
        //        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        // constant -> EndPoint'ede koyabiliriz bu kısmı
        //  @NotBlank (message = "Şifre boş geçilemez.")
        //@Pattern(regexp = PASSWORD_PATTERN)
        @NotBlank(message="Şifre boş geçilemez")
        private String password;
        private String rePassword;
    }




