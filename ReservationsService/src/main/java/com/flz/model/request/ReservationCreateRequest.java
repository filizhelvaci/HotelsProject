package com.flz.model.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationCreateRequest {

    String email;
    String name;
    String lastname;
    byte userType;

}
