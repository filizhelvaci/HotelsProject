package com.flz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class BasePerson {

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

    // @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date updateAt;

    boolean state;
}
