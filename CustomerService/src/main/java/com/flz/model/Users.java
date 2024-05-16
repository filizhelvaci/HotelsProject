package com.flz.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Data//set get metotlarını otomatik tanımlar
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Users {

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Customer> customer = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long UserId;

    @Column(name="NAME",nullable = false,length = 30)
    private String name;

    @Column(name="LASTNAME",nullable = false,length = 30)
    private String lastName;

    @Column(name="IDENTY_NUMBER",nullable = false,unique = true,length = 20)
    private String IDnumber;

    @Column(name="E_MAIL",length = 50)
    private String Email;

    @Column(name="PHONE_NUMBER",length = 20)
    private String PhoneNumber;
/*
    @Column(name="BIRTHDATE",nullable = false)
    private String birthDate;

    @Column(name="NATIONALITY")
    private String nationality;

    @Column(name="CITY",nullable = false,length = 50)
    private String city; */

    // rezervationMake(List<roomId>,dates,###price)
    // rezervationCancel(List<roomId>,dates,###price)
}
