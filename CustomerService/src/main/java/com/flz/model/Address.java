package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Address {

    @Column(name="COUNTRY",nullable = false,length = 50)
    private String country;

    @Column(name="CITY",nullable = false,length = 50)
    private String city;

    @Column(name="DISTRICT",length = 50)
    private String district;

    @Column(name="STREET",length = 50)
    private String street;




}
