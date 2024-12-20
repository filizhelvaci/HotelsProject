package com.flz.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DoReservationRequestDto {

    String name;
    String lastname;
    String email;
    byte userType;
}
