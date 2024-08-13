package com.flz.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AdditionalFeature {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToMany(mappedBy = "addFeature")
        private Set<Rooms> rooms = new HashSet<>();

}
