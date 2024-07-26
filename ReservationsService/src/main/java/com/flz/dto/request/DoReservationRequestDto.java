package com.flz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

public class DoReservationRequestDto {

    private Long id;
    private String eMail; //userdan
    private String name; //userdan
    private String lastName; //userdan
    private Long roomId;
    private Date startDate;
    private Date endDate;
    private Byte userType; // userdan
}
