package com.flz.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;

@Entity
@Table(name="ROOMS")
public class Rooms {


    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="ROOM_TYPE_ID")
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="REZERVATION_ID")
    private MakeRezervation makeRezervation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROOM_ID")
    private Long roomId;

    @Column(name="ROOM_NUMBER")
    private String roomNumber;

    @Column(name="ROOM_TYPE_ID")
    private Long roomTypeId;

    @Column(name="FLOOR_NUMBER")
    private String whichfloor;

    @Column(name="MAX_PERSON_COUNT")
    private int countPerson;

    @Column(name="DOUBLE_BED_COUNT")
    private int CountOfDoubleBed;

    @Column(name="SINGLE_BED_COUNT")
    private int CountOfSingleBed;

    //private ArrayList<String> roomFurnitures;
    //private ArrayList<String> roomProperties;

    @Column(name="SIZE")
    private int m2;

    @Column(name="HOTEL_ID")
    private Long hotel_id;

    @Column(name="PRICE")
    private float price;

    @Column(name="IS_FULL")
    private boolean isFull;

    @Column(name="DESCRIPTION")
    private String description;

    /////////////// rezervasyon yapıldığında nasıl olmalı///////////////

}
