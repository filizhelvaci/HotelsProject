package com.flz.model.request;

import com.flz.model.enums.Gender;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
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

    @NotNull
    @Range(min = 0, max = 10_000_000)
    @Positive
    private BigDecimal salary;

    @NotNull
    private Long positionId;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long supervisorId;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

}
