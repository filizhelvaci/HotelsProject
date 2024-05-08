package com.flz.model;


import jakarta.persistence.*;

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

    private BasePerson basePerson;
    private BaseConnectionInfo connectionInfo;

    @Column(name="CITY",nullable = false,length = 50)
    private String city;

    // rezervationMake(List<roomId>,dates,###price)
    // rezervationCancel(List<roomId>,dates,###price)
}
