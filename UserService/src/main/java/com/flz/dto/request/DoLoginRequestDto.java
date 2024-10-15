package com.flz.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DoLoginRequestDto {

    private String email;
    private String password;
}
