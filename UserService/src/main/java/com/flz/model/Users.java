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

    //  ********************************************
    // Rezervasyon yaptırmak istediğinde bu bilgileri girmek zorunda
    // dto ile customer db kısmına user bilgileri gönderilmeli
    // Sisteme kayıt için sadece email ve password yeterli
    // *********************************************

    //User and MakeRezervation link
    /*@OneToOne (mappedBy = "users",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private MakeRezervation maketheRezervation; */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
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



}
