package com.flz.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="MAKE_REZERVATION")
public class MakeRezervation {
    /*
    // rezervation and rooms link
    @OneToMany(mappedBy = "makeRezervation",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Rooms> selectedRooms=new ArrayList<>();

    //Rezervation and Users link (?)
    @OneToOne(mappedBy = "maketheRezervation",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private Users users;
*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//(?)
    //@GenericGenerator(name = "generator", strategy = "foreign",
    //        parameters = @Parameter(name = "property", value = "users"))
    @Column(name="REZERVATION_ID",nullable = false)
    private Long rezervationId;

    @Temporal(TemporalType.DATE)
    @Column(name="REZERVATION_START_DATE",nullable = false,length = 15)
    private Date reservationStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name="REZERVATION_END_DATE",nullable = false,length = 15)
    private Date rezervationEndDate;

    @Column(name="REZERVATION_MAKE_DATE",length =20)
    private Date rezervationMakeDate;

    @Column(name="USER_ID")
    private Long userId;

    private boolean IsPay; // if (true) makeTheRezervation(DTO);
    private boolean IsDeposit; // if (true) makeTheRezervation(DTO)

    // payForRooms();
    // makeADeposit();



}
