package com.flz.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoReservationRequestDto {

    private String name;
    private String lastName;
    private String eMail;
    private byte userType;
}
