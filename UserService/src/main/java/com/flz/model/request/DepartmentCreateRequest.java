package com.flz.model.request;

import com.flz.model.enums.DepartmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentCreateRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    private DepartmentStatus status;

}
