package com.flz.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString


@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID",nullable = false)
    private Long Id;

    @Column(name="USER_ID")
    private Long userId; // odayı tutan kişi

    @Column(name="NAME",nullable = false)
    private String name;

    @Column(name="LASTNAME",nullable = false)
    private String lastName;

    @Column(name="BIRTHDATE",nullable = false)
    private String birthDate;

    @Column(name="IDENTY_NUMBER",nullable = false,unique = true)
    private String IDnumber;

    @Column(name="NATIONALITY")
    private String nationality;

    @Column(name="E_MAIL")
    private String Email;

    @Column(name="PHONE_NUMBER")
    private String PhoneNumber;

    @Column(name="ENTER_DATE")
    private Date enterDate;

    @Column(name="EXIT_DATE")
    private Date exitDate;

    @Column(name="ROOM_NUMBER")
    private String roomNumber;

    @Column(name="CITY",nullable = false,length = 50)
    private String city;



}
