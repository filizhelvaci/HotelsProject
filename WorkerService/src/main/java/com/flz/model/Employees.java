package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="EMPLOYEES")
public class Employees  {

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="POSITION_ID")
    private Position position;

    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (name = "EMPLOYEES_ADDRESS",
            joinColumns = { @JoinColumn(name = "EMPLOYEES_ID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID", nullable = false)}
    )
    private Set<Address> address = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="EMPLOYEES_ID",nullable = false)
    private Long employeesId;

    @Column(name="NAME",nullable = false)
    private String name;

    @Column(name="LASTNAME",nullable = false)
    private String lastName;

    @Column(name="INSIDE_NUMBER",nullable = false)
    private String insideNumber;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Column(name="E_MAIL")
    private String Email;

    @Column(name="BIRTHDATE",nullable = false)
    private String birthDate;

    @Column(name="IDENTY_NUMBER",nullable = false,unique = true)
    private String IDnumber;

    @Column(name="NATIONALITY")
    private String nationality;

    @Column(name="SALARY",nullable = false)
    private Long salary;

    @Column(name="START_DATE",nullable = false)
    private Date startDate;

    @Column(name="EXIT_DATE")
    private Date exitDate;

    @Column(name="POSITION_ID",nullable = false, length = 5)
    private Long position_id;




}
