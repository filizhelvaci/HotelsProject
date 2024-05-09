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

    //User and MakeRezervation link
    /*@OneToOne (mappedBy = "users",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private MakeRezervation maketheRezervation; */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long userId;

    @Embedded
    private BasePerson basePerson;

    @Embedded
    private BaseConnectionInfo baseConnectionInfo;

    @Column(name="CITY",nullable = false,length = 50)
    private String city;

    // rezervationMake(List<roomId>,dates,###price)
    // rezervationCancel(List<roomId>,dates,###price)
}
