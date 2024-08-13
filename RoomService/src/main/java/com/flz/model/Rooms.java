package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="ROOMS")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROOM_ID")
    private Long Id;

    @Column(name="ROOM_NUMBER",nullable = false,length = 5)
    private String roomNumber;

    @Column(name="FLOOR_NUMBER",nullable = false,length = 3)
    private String whichfloor;

    @Column(name="MAX_PERSON_COUNT",nullable = false,length = 3)
    private int countPerson;

    @Column(name="SIZE",length = 4,nullable = false)
    private int m2;

    @Column(name="PRICE")
    private float price;

    @Column(name="IS_FULL")
    private boolean isFull;

    @Column(name="IS_RESERVE")
    private boolean isReserve;

    @Column(name="IS_IN_MAINTENANCE")
    private boolean isInMaintenance;

    @Column(name="DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "room_add_feature",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "add_feature_id")
    )
    private Set<AdditionalFeature> addFeature = new HashSet<>();

//    @Column(name="DOUBLE_BED_COUNT",nullable = false,length = 3)
//    private int CountOfDoubleBed;
//
//    @Column(name="SINGLE_BED_COUNT",nullable = false,length = 3)
//    private int CountOfSingleBed;

//    @Column(name="ROOM_COUNT",nullable = false,length = 5)
//    private float roomCount;

//    @Column(name="ROOM_PROPERTIES",nullable = false)
//    private String roomProperties;
//
//    // Rooms          RoomTypes
//    //    m               1
//    @ManyToOne(fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name="ROOM_TYPE_ID")
//    private RoomType roomType;
//
//    // Rooms            Hotel
//    //    m               1
//    @ManyToOne(fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name="HOTEL_ID")
//    private Hotel hotel;

}
