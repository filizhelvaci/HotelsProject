package com.flz.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table(name = "rs_room_type")
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "person_count")
    private Integer personCount;

    @Column(name = "size")
    private Integer size;

    @Column(name = "description")
    private String description;

}
