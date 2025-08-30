package com.flz.model;

import com.flz.model.enums.PositionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Position extends BaseDomainModel {

    private Long id;
    private String name;
    private PositionStatus status;

    private Department department;

    public void delete() {
        this.status = PositionStatus.DELETED;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "ADMIN";
    }

    public boolean isDeleted() {

        return this.status == PositionStatus.DELETED;
    }

}
