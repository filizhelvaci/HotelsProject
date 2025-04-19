package com.flz.model.response;

import com.flz.model.enums.DepartmentStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class DepartmentResponse {

    private Long id;
    private String name;
    private DepartmentStatus status;

}
