package com.flz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoRegisterResponseCustomerDto {


    private String email;
    private String name;
    private String lastname;
    private String IDnumber;
}
