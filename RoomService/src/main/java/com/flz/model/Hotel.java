package com.flz.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="HOTEL_NAME")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HOTEL_ID")
    private Long id;

    @Column(name="HOTEL_NAME",nullable = false,length = 100)
    private String hotelName;

    @Column(name="HOTEL_TYPE",nullable = false,length = 50)
    private String hotelType;

    @Column(name="ROOM_COUNT",nullable=false,length = 5)
    private int roomCount;

    @Column(name="HOTEL_PROPERTIES",nullable = false)
    private String hotelProperties ;

    @Column(name="INSTAGRAM_ADDRESS")
    private String instagramAddress;

    @Column(name="DESCRIPTION",nullable = false)
    private String Description;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<Rooms> rooms = new HashSet<>();

    //  Hotel             Address
    //    1                 1
    @OneToOne(mappedBy = "customer",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    private Address address;

    //  Hotel             RoomType
    //    m                 m
    @ManyToMany
    @JoinTable(name = "HOTEL_ROOM_TYPES",
            joinColumns = @JoinColumn (name = "HOTEL_ID"),
            inverseJoinColumns = @JoinColumn (name = "ROOM_TYPE_ID")
    )
    private Set<RoomType> roomTypes = new HashSet<>();




}
