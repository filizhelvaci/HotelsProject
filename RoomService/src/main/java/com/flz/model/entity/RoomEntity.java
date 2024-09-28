package com.flz.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Table(name = "rs_room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "floor")
    private String floor;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "status")
    private String status;

}
