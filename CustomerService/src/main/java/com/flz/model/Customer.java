package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

//@EqualsAndHashCode(callSuper = true)
@SuperBuilder // bir sınıftan nesne türetmek için
@Data//set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
@ToString


@Entity
@Table(name="CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CUSTOMER_ID",nullable = false)
    private Long customerId;

    private Long userId; // odayı tutan kişi

    @Embedded
    private Address address;

    @Embedded
    private BasePerson basePerson;

    @Column(name="ENTER_DATE")
    private Date enterDate;

    @Column(name="EXIT_DATE")
    private Date exitDate;

    @Column(name="ROOM_NUMBER")
    private String roomNumber;




}
