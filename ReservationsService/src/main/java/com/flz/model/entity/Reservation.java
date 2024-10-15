package com.flz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String name;
    private String lastname;
    private String email;
    private byte userType;
    private Date startDate;
    private Date endDate;
    private String paymentMethods;

}
