package com.flz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

//@EqualsAndHashCode(callSuper = true)
@Builder // bir sınıftan nesne türetmek için
@Data//set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Employees  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMPLOYES_ID",nullable = false)
    private Long employeesId;

    @Column(name="POSITION_ID",nullable = false, length = 5)
    private Long position_id;

    @Column(name="DEPARTMENT_ID",nullable = false ,length = 10)
    private Long department_id;

    @Column(name="ADDRESS_ID",nullable = false, length = 5)
    private Long addressId;

    @Embedded
    private BaseConnectionInfo baseConnectionInfo;

    @Embedded
    private BasePerson basePerson;

    @Column(name="SALARY",nullable = false)
    private Long salary;

    @Column(name="START_DATE",nullable = false)
    private Date startDate;

    @Column(name="EXIT_DATE")
    private Date exitDate;




}
