package com.flz.model;

import com.flz.exception.DepartmentAlreadyDeletedException;
import com.flz.model.enums.DepartmentStatus;
import lombok.Builder;
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
    @Builder.Default
    private DepartmentStatus status = DepartmentStatus.ACTIVE;

    private Employee manager;

    public void update(String name, Employee manager) {

        this.name = name;
        this.manager = manager;
        this.status = DepartmentStatus.ACTIVE;
    }

    public void delete(Long id) {

        if (this.isDeleted()) {
            throw new DepartmentAlreadyDeletedException(id);
        }

        this.status = DepartmentStatus.DELETED;
    }

    public boolean isDeleted() {

        return this.status == DepartmentStatus.DELETED;
    }

    public void activate() {

        this.status = DepartmentStatus.ACTIVE;
    }

}
