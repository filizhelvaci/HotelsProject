package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name="POSITIONS")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="POSITION_ID",nullable = false)
    private Long id;

    @Column(name = "POSITION_NAME",nullable = false)
    private String positionName;


    ////////////////////////////////////////////////////////////////
    private String YETKILER;
    ////////////////////////////////////////////////////////////////
}
