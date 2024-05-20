package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Department {

    @Column(name = "DEPARTMENT_NAME",nullable = false)
    private String DepartmentName;

    private Date DepartmentEstablishmentDate;





}

