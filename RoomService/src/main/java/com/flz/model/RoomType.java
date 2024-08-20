package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="ROOM_TYPES")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROOM_TYPE_ID")
    private Long Id;

    @Column(name="ROOM_TYPE_NAME",nullable = false,length = 50)
    private String RoomTypeName;

    @Column(name="ADDITIONAL_PRICE",nullable = false,length = 15)
    private float additionalPrice;

    // fixme : ek özellikler atanacak
    // private String AdditionalFeatures
//
////    // RoomType           Hotel
////    //    m                 m
//    @ManyToMany(mappedBy = "roomTypes",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private Set<Hotel> hotels = new HashSet<>();

//    // RoomType           Rooms
//    //     1                m
    @OneToMany(mappedBy = "roomType",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<Rooms> roomT = new HashSet<>();

    // RoomType          AdditionalFeature
    //    m                    m
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable (name = "ROOM_TYPES_ADDITIONALFEATURES",
            joinColumns = { @JoinColumn(name = "ROOM_TYPE_ID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "ADD_FEA_ID", nullable = false)}
    )
    private List<AdditionalFeature> additionalFeatures = new ArrayList<>();



}
