package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; //LİST

    private String name;
    private String lastname;
    private String email;
    private byte userType;

    private Date startDate;
    private Date endDate;

    private String paymentMethods;

    /*

    // rezervation and rooms link
    @OneToMany(mappedBy = "makeRezervation",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Rooms> selectedRooms=new ArrayList<>();

    //Rezervation and Users link (?) // USERDAN DTO GELİCEK
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
