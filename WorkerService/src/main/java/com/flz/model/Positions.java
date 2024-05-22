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
public class Positions extends Departments {

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<Employees> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "position_permission",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permissions> permissions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "position_role",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();


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
