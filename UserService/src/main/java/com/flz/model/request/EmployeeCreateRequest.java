package com.flz.model.request;

import com.flz.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
    @Positive
    private Long positionId;

    @NotNull
    @Positive
    private Long departmentId;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

}
