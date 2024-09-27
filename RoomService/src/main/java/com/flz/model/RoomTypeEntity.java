package com.flz.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="RS_ROOM_TYPE")
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME",nullable = false,length = 50)
    private String name;

    @Column(name="PRICE",nullable = false,length = 15)
    private BigDecimal price;

    @Column(name="PERSON_COUNT",nullable = false,length = 3)
    private short personCount;

    @Column(name="SIZE",length = 4,nullable = false)
    private short size;

    @Column(name="DESCRIPTION",length = 250)
    private String description;

}
