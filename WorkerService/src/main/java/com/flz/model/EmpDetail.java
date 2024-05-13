package com.flz.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="EMP_DETAIL")
public class EmpDetail {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeesId")
    private Employees employees;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="E_ID")
    private Long id;

    @Column(name="ADDRESS_ID",nullable = false, length = 5)
    private Long addressId;

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

    @Column(name="DEPARTMENT_ID",nullable = false ,length = 10)
    private Long department_id;
}
