package com.flz.model;

import jakarta.persistence.*;
@Entity
@Table(name="ROOM_TYPES")
public class RoomType {
  /*
    @OneToMany(mappedBy = "roomType",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Rooms> rooms=new ArrayList<>(); */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROOM_TYPE_ID")
    private Long RoomTypeId;

    @Column(name="ROOM_TYPE_NAME",nullable = false,length = 50)
    private String RoomTypeName;

    @Column(name="ROOM_COUNT",nullable = false,length = 5)
    private int RoomCount;
}
