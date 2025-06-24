package com.flz.model;

import com.flz.model.enums.DepartmentStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseDomainModel {

    private Long id;
    private String name;
    private DepartmentStatus status;

    public void delete() {
        this.setStatus(DepartmentStatus.DELETED);
        this.setUpdatedAt(LocalDateTime.now());
        this.setUpdatedUser("ADMIN");
    }

}
