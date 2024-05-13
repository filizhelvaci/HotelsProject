package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

//@EqualsAndHashCode(callSuper = true)
@Builder // bir sınıftan nesne türetmek için
@Data//set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="EMPLOYEES")
public class Employees  {

    @OneToOne(mappedBy = "employees", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmpDetail empDetail;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMPLOYES_ID",nullable = false)
    private Long employeesId;

    @Column(name="NAME",nullable = false)
    private String name;

    @Column(name="LASTNAME",nullable = false)
    private String lastName;

    @Column(name="INSIDE_NUMBER",nullable = false)
    private String insideNumber;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;


}
