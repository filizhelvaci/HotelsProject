package com.flz.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;



public class reservation {
    private String roomId;
    private String userId;

    private Date startDate;
    private Date exitDate;
    /*

    // rezervation and rooms link
    @OneToMany(mappedBy = "makeRezervation",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Rooms> selectedRooms=new ArrayList<>();

    //Rezervation and Users link (?)
    @OneToOne(mappedBy = "maketheRezervation",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private Users users;


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


    //private List<Long> roomId;

    private boolean IsPay; // if (true) makeTheRezervation(DTO);
    private boolean IsDeposit; // if (true) makeTheRezervation(DTO)

    // payForRooms();
    // makeADeposit();


    */

}
