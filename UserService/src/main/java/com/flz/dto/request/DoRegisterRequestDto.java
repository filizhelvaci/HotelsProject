package com.flz.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoRegisterRequestDto {

        private String name;
        private String lastname;

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
        @NotBlank(message="Şifre boş geçilemez")
        private String rePassword;
    }




