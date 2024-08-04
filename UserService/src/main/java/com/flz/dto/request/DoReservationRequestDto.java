package com.flz.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoReservationRequestDto {

    String name;
    String lastname;
    String email;
     byte userType;
}
