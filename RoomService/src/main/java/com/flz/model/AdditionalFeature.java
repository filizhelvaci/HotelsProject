package com.flz.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AdditionalFeature {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="FEATURE_NAME",nullable = false,length = 50)
        private String featureName;

        @Column(name="DESCRIPTION")
        private String description;

        @Column(name="PRICE",nullable = false,length = 15)
        private float price;

        @ManyToMany(mappedBy = "addFeature")
        private Set<Rooms> rooms = new HashSet<>();

}
