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

}
