package com.flz.model.entity;

import jakarta.persistence.*;
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
@Table(name = "ru_employee_old_experience")
public class EmployeeOldExperienceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * ----------------------------------------------------------------
     * EmployeeOldExperienceEntity      PositionEntity
     * M                       1
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private PositionEntity position;

    /**
     * ----------------------------------------------------------------
     * EmployeeOldExperienceEntity      EmployeeOldEntity
     * M                       1
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_old_id", referencedColumnName = "id", nullable = false)
    private EmployeeOldEntity employeeOld;

    /**
     * ----------------------------------------------------------------
     * EmployeeOldExperienceEntity      EmployeeEntity
     * M                       1
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity supervisor;

}
