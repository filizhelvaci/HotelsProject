package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name="POSITIONS")
public class Position extends Department {

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<Employees> employees = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="POSITION_ID",nullable = false)
    private Long Id;

    @Column(name = "POSITION_NAME",nullable = false)
    private String positionName;



    ////////////////////////////////////////////////////////////////
    private String authorityClass;
    ////////////////////////////////////////////////////////////////
}
