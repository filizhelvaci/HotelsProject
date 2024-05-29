package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPARTMENT_ID")
    private Long id;

    @Column(name = "DEPARTMENT_NAME",nullable = false)
    private String DepartmentName;

    private Date DepartmentEstablishmentDate;

//
//    //----------------------------------------------------------------
//    //  Departments     Positions
//    //     1               M
//    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    private Set<Positions> positions = new HashSet<>();






}

