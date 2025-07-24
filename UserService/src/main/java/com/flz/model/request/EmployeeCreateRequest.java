package com.flz.model.request;

import com.flz.model.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;
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
    @Pattern(regexp = "^[1-9]\\d{10}$", message = "Invalid identity number")
    private String identityNumber;

    @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{10,13}$", message = "Phone number must be 10 to 13 digits, optionally starting with '+'")
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
