package com.flz.model;

import com.flz.model.enums.PositionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Position extends BaseDomainModel {

    private Long id;
    private String name;
    private PositionStatus status;

    private Department department;

    public void create(Department department) {

        this.department = department;
        this.status = PositionStatus.ACTIVE;
    }

    public void update(String name, Department department) {

        this.name = name;
        this.department = department;
        this.status = PositionStatus.ACTIVE;
    }

    public void delete() {

        this.status = PositionStatus.DELETED;
    }

    public boolean isDeleted() {

        return this.status == PositionStatus.DELETED;
    }

    public void active() {

        this.status = PositionStatus.ACTIVE;
    }

}
