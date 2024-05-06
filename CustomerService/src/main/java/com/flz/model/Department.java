package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name="DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPARTMENT_ID",nullable = false)
    private Long id;

    @Column(name = "DEPARTMENT_NAME",nullable = false)
    private String DepartmentName;

}

