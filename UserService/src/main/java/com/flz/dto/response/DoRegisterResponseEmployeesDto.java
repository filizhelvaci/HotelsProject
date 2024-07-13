package com.flz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoRegisterResponseEmployeesDto {

    private String password;
    private String email;
    private String name;
    private String lastname;
    private Long id;

}
