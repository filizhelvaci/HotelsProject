package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

//@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="EMPLOYEES")
public class Employees  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="UID",nullable = false)
    private Long UId;

    @Column(name="NAME",nullable = false)
    private String name;

    @Column(name="LASTNAME",nullable = false)
    private String lastName;

    @Column(name="INSIDE_NUMBER",nullable = false)
    private String insideNumber;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

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
