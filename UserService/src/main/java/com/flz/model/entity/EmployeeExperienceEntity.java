package com.flz.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ru_employee_experience")
public class EmployeeExperienceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "transition_date")
    private LocalDate transitionDate;

    /**
     * ----------------------------------------------------------------
     * EmployeeExperienceEntity      PositionEntity
     * M                       1
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private PositionEntity position;

    /**
     * ----------------------------------------------------------------
     * EmployeeExperienceEntity      EmployeeEntity
     * M                       1
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity employee;

    /**
     * ----------------------------------------------------------------
     * EmployeeExperienceEntity      EmployeeEntity
     * M                       1
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity supervisor;
}
