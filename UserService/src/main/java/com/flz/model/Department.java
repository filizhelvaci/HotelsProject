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

    private Employee manager;

    public void delete() {
        this.status = DepartmentStatus.DELETED;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "ADMIN";
    }

    public boolean isDeleted() {

        return this.status == DepartmentStatus.DELETED;
    }

    public void doActive() {

        this.status = DepartmentStatus.ACTIVE;
    }

}
