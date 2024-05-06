package com.flz.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name="ROOMS")
public class Rooms {


    /////////////////////////////// Hem room number hem room ıd ye gerek var mı? /////////////////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROOM_ID")
    private Long roomId;

    @Column(name="ROOM_NUMBER")
    private String roomNumber;

    @Column(name="ROOM_TYPE")
    private String roomType;

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

}
