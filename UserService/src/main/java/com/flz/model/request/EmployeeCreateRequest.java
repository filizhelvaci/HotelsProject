package com.flz.model.request;

import com.flz.model.EmployeeExperience;
import com.flz.model.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 9, max = 15)
    private String identityNumber;

    @Size(min = 2, max = 255)
    private String email;

    @NotBlank
    @Size(min = 2, max = 13)
    private String phoneNumber;

    @NotBlank
    @Size(min = 2, max = 255)
    private String address;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    @Size(min = 2, max = 100)
    private String nationality;

    @NotEmpty
    private EmployeeExperience experience;

}
