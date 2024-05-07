package com.flz.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="HOTEL_NAME")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HOTEL_ID")
    private Long hotelId;

    @Column(name="HOTEL_NAME",nullable = false)
    private String hotelName;

    private int starCount;

    private String hotelType;
    private int roomCounts;

    //private ArrayList<String> hotelProperties ;
    private String Description;
}
