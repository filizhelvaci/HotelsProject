package com.flz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="EMPLOYEES")
public class Employees  {
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="POSITION_ID")
    private Positions position;

    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (name = "EMPLOYEES_ADDRESS",
            joinColumns = { @JoinColumn(name = "EMPLOYEES_ID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID", nullable = false)}
    )
    private Set<Address> address = new HashSet<>();
   */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
