package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "district", length = 50)
    private String district;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "street_number", length = 5)
    private String streetNumber;

    /**
     * -----------------------------------------------------------------------------------
     * Address           Users
     * M                M
     */
    @ManyToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<Users> users = new HashSet<>();

}
