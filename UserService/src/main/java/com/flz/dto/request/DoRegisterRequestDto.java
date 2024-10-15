package com.flz.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DoRegisterRequestDto {

        private String name;
        private String lastname;
        @Email
        private String email;
        private String phoneNumber;
        @NotBlank(message="Şifre boş geçilemez")
        private String password;
        @NotBlank(message="Şifre boş geçilemez")
        private String rePassword;
    }




