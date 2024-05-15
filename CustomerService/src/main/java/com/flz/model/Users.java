package com.flz.model;


import jakarta.persistence.*;
import lombok.*;

@Data//set get metotlarını otomatik tanımlar
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Users {

    //User and MakeRezervation link
    /*@OneToOne (mappedBy = "users",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private MakeRezervation maketheRezervation; */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long UserId;

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

    @Column(name="CITY",nullable = false,length = 50)
    private String city;

    // rezervationMake(List<roomId>,dates,###price)
    // rezervationCancel(List<roomId>,dates,###price)
}
