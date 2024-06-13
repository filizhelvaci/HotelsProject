package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Employees extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMPLOYEES_ID",nullable = false)
    private Long Id;

    @Column(name="INSIDE_NUMBER",nullable = false)
    private String insideNumber;

    @Column(name="BIRTHDATE",nullable = false)
    private String birthDate;

    @Column(name="IDENTY_NUMBER",nullable = false,unique = true)
    private String IDnumber;

    @Column(name="CONTRACT_PERIOD",nullable = false)
    private int contractPeriod;

    @Column(name="GRADUATION_STATUS")
    private String graduationStatus;

    @Column(name="GRADUATION_YEAR")
    private String graduationYear;

    //-----------------------------------------------------------------------------------
    //  Employees       Positions
    //     M                M
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (name = "EMPLOYEES_POSITIONS",
            joinColumns = { @JoinColumn(name = "EMPLOYEES_ID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "POSITION_ID", nullable = false)}
    )
    private Set<Positions> positions = new HashSet<>();

    //-----------------------------------------------------------------------------------
    //  Employees       Address
    //     M                M
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (name = "EMPLOYEES_ADDRESS",
            joinColumns = { @JoinColumn(name = "EMPLOYEES_ID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID", nullable = false)}
    )
    private Set<Address> address = new HashSet<>();

    //-----------------------------------------------------------------------------------
    //  Employee      User
    //      1          1
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID")
    private Users user;

    //-----------------------------------------------------------------------------------
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long GuestServiceId;
    private String GraduatedDepartment;

    private String Experiences;

    private String Skills;

    //CleaningReport(roomid,date,generalWorker_id)
    //CleaningControl() //Eğer extra temizlik ücreti varsa
    //FoodServiceReport(roomid,date,)
    */

}
