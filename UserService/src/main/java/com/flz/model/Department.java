package com.flz.model;

import com.flz.model.enums.DepartmentStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseDomainModel {

    private Long id;
    private String name;
    private DepartmentStatus status;

}
