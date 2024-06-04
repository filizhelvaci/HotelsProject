package com.flz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Employees  {

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

    @Column(name="NATIONALITY")
    private String nationality;

    @Column(name="SALARY",nullable = false)
    private Long salary;

    @Column(name="START_DATE",nullable = false)
    private Date startDate;

    @Column(name="EXIT_DATE")
    private Date exitDate;

    @Column(name="GRADUATION_STATUS")
    private String graduationStatus;

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
