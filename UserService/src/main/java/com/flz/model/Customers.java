package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Customers extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Byte identyPhoto;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "identy_number")
    private String IDnumber;

    @Column(name = "nationalty")
    private String nationality;

    /**
     * -----------------------------------------------------------------------------------
     * Customer     User
     * 1          1
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Users user;


}
