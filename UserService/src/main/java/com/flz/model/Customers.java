package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Customers extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CUSTOMER_ID")
    private Long Id;

    private Byte identyPhoto;

    @Column(name="BIRTHDATE",nullable = false,length = 20)
    private String birthDate;

    @Column(name="IDENTY_NUMBER",nullable = false,unique = true,length = 15)
    private String IDnumber;

    @Column(name="NATIONALITY",length = 20)
    private String nationality;


// FIXME kimlik bilgileri girilecek

/*  FIXME bu alanları rezervasyon ve oda'ya gönder
    @Column(name="ENTER_DATE",length = 25)
    private Date enterDate;

    @Column(name="EXIT_DATE",length = 25)
    private Date exitDate;

    @Column(name="ROOM_NUMBER",length = 25)
    private String roomNumber; */

    //-----------------------------------------------------------------------------------
    //   Customer     User
    //      1          1
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID",referencedColumnName = "id")
    private Users user;


}
