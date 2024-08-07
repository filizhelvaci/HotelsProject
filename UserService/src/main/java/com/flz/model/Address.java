package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="ADDRESS")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ADDRESS_ID")
    private Long Id;

    @Column(name="COUNTRY",nullable = false,length = 50)
    private String country;

    @Column(name="CITY",nullable = false,length = 50)
    private String city;

    @Column(name="DISTRICT",length = 50)
    private String district;

    @Column(name="STREET",length = 50)
    private String street;

    @Column(name="STREET_NUMBER",length = 5)
    private String streetNumber;

    //-----------------------------------------------------------------------------------
    //  Address           Users
    //     M                M
//    @ManyToMany(mappedBy = "address", fetch = FetchType.LAZY)
//    private Set<Users> users = new HashSet<>();

}
