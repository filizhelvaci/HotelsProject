package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Positions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="POSITION_ID",nullable = false)
    private Long Id;

    @Column(name = "POSITION_NAME",nullable = false)
    private String positionName;

    ////////////////////////////////////////////////////////////////
    // FIXME
    private String authorityClass;
    ////////////////////////////////////////////////////////////////

//
//    //----------------------------------------------------------------
//    //  Positions      Employees
//    //     M              M
//    @ManyToMany(mappedBy = "positions", fetch = FetchType.LAZY)
//    private Set<Employees> employees = new HashSet<>();
//
//
//    //----------------------------------------------------------------
//    //  Positions      Permission
//    //     M              M
//    @ManyToMany
//    @JoinTable(
//            name = "position_permission",
//            joinColumns = @JoinColumn(name = "position_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
//    private Set<Permissions> permissions = new HashSet<>();
//
//    //----------------------------------------------------------------
//    //  Positions      Roles
//    //     M              M
//    @ManyToMany
//    @JoinTable(
//            name = "position_role",
//            joinColumns = @JoinColumn(name = "position_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Roles> roles = new HashSet<>();
//
//    //----------------------------------------------------------------
//    //  Positions      Departments
//    //     M               1
//    @ManyToOne(fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "DEPARTMENT_ID")
//    private Departments department;
//

}
