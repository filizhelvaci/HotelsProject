package com.flz.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="RS_ROOM")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID",nullable = false)
    private Long id;

    @Column(name="NUMBER",nullable = false,length = 5)
    private int number;

    @Column(name="FLOOR",nullable = false,length = 3)
    private String floor;

    @Column(name="TYPE_ID",nullable = false)
    private Long typeId;

    @Column(name="STATUS", nullable = false )
    private String status;

}
