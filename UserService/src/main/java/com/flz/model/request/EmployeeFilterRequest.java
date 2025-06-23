package com.flz.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EmployeeFilterRequest {

    @Range(min = 1)
    @NotNull
    private Integer page;

    @NotNull
    @Range(min = 1, max = 15)
    private Integer pageSize;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Past
    private LocalDate startDateFrom;

    @NotNull
    @Past
    private LocalDate startDateTo;

    @NotNull
    private List<Long> positionIds;

    @NotNull
    private List<Long> departmentIds;
}
