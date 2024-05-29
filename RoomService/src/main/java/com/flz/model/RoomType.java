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
@Table(name="ROOM_TYPES")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROOM_TYPE_ID")
    private Long Id;

    @Column(name="ROOM_TYPE_NAME",nullable = false,length = 50)
    private String RoomTypeName;

    @Column(name="ROOM_COUNT",nullable = false,length = 5)
    private int RoomCount;

//    // RoomType           Hotel
//    //    m                 m
//    @ManyToMany(mappedBy = "roomTypes",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private Set<Hotel> hotels = new HashSet<>();
//
//    // RoomType           Rooms
//    //    m                 m
//    @OneToMany(mappedBy = "roomType",fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    private Set<Rooms> roomT = new HashSet<>();
}
