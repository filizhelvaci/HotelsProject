package com.flz.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
public abstract class BaseDomainModel {

    protected String createdUser;
    protected LocalDateTime createdAt;
    protected String updatedUser;
    protected LocalDateTime updatedAt;

}
