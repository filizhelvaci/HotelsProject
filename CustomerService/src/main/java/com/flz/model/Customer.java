package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Customer {

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "USER_ID")
    private Users users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UID",nullable = false)
    private Long UId;

    @Column(name="NAME",nullable = false,length = 50)
    private String name;

    @Column(name="LASTNAME",nullable = false,length = 50)
    private String lastName;

    @Column(name="BIRTHDATE",nullable = false,length = 20)
    private String birthDate;

    @Column(name="IDENTY_NUMBER",nullable = false,unique = true,length = 15)
    private String IDnumber;

    @Column(name="NATIONALITY",length = 20)
    private String nationality;

    @Column(name="E_MAIL",length = 50)
    private String Email;

    @Column(name="PHONE_NUMBER",length = 20)
    private String PhoneNumber;

    @Column(name="ENTER_DATE",length = 25)
    private Date enterDate;

    @Column(name="EXIT_DATE",length = 25)
    private Date exitDate;

    @Column(name="ROOM_NUMBER",length = 25)
    private String roomNumber;

    @Column(name="CITY",nullable = false,length = 50)
    private String city;



}
