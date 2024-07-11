package com.flz.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // bir sınıfın kolayca nesne oluşturmasını sağlar
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
public class DoLoginRequestDto {

    private String Email;
    @Size(min=6, max=15)
    private String password;
}
