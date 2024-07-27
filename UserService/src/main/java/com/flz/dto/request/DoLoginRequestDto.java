package com.flz.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoLoginRequestDto {

    private String Email;
    //@Size(min=6, max=15)
    private String password;
}
