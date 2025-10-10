package com.flz.model;

import com.flz.exception.PositionAlreadyDeletedException;
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

    public void update(String name, Department department) {

        this.name = name;
        this.department = department;
        this.status = PositionStatus.ACTIVE;
    }

    public void delete(Long id) {

        if (this.isDeleted()) {
            throw new PositionAlreadyDeletedException(id);
        }
        this.status = PositionStatus.DELETED;
    }

    public boolean isDeleted() {

        return this.status == PositionStatus.DELETED;
    }

    public void activate() {

        this.status = PositionStatus.ACTIVE;
    }

}
