package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name="EMPLOYEES_ID",nullable = false)
    private Long employeesId;

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




}
