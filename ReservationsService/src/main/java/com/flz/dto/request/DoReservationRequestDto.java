package com.flz.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Builder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoReservationRequestDto {

    String email; //userdan
    String name; //userdan
    String lastname; //userdan
    byte userType; // userdan

//
//    private String roomId;
//
//    private Date startDate;
//    private Date endDate;
}
