package com.flz.model;

import jakarta.persistence.*;

@Entity
@Table
public class Rezervation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REZERVATION_ID")
    private Long id;

    private Long roomId;
}
