package com.flz.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data//set get metotlarını otomatik tanımlar
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UID",unique = true)
    private Long Id;

    @Column(name="NAME",nullable = false,length = 30)
    private String name;

    @Column(name="LASTNAME",nullable = false,length = 30)
    private String lastName;

    @Column(name="E_MAIL",nullable = false,length = 50,unique = true)
    private String Email;

    @Column(name="PHONE_NUMBER",nullable = false,length = 20)
    private String PhoneNumber;

    @Column(name="PASSWORD",nullable = false,length = 13)
    private String Password;

    //-----------------------------------------------------------------------------------
    //  Users           Address
    //     M                M
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (name = "USERS_ADDRESS",
            joinColumns = { @JoinColumn(name = "UID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID", nullable = false)}
    )
    private Set<Address> address = new HashSet<>();

    //-----------------------------------------------------------------------------------
    //   Users            Customers
    //     1                 1
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customers customer;

    //-----------------------------------------------------------------------------------
    //   Users            Employees
    //     1                 1
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employees employee;

//    //  ********************************************
//    // Rezervasyon yaptırmak istediğinde bu bilgileri girmek zorunda
//    // dto ile customer db kısmına user bilgileri gönderilmeli
//    // Sisteme kayıt için sadece email ve password yeterli
//    // *********************************************
//
//    //User and MakeRezervation link
//    /*@OneToOne (mappedBy = "users",
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private MakeRezervation maketheRezervation; */


}
