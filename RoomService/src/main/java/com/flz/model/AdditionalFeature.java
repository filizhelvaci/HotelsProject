package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class AdditionalFeature extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="ADD_FEA_ID")
        private Long id;

        @Column(name="FEATURE_NAME",nullable = false,length = 50)
        private String featureName;

        @Column(name="DESCRIPTION")
        private String description;

        @Column(name="PRICE",nullable = false,length = 15)
        private float price;

        @Column(name = "BRAND",nullable = false,length = 50)
        private String brand;

        @Column(name="COLOR",nullable = false,length = 25)
        private String color;

        // AdditionalFeature   Inventory
        //   1                    m
        @OneToMany(mappedBy = "additionalFeature", fetch = FetchType.LAZY)
        @Fetch(FetchMode.SELECT)
        private Set<Inventory> inventories = new HashSet<>();

        @ManyToMany(mappedBy = "addFeature")
        private Set<Rooms> rooms = new HashSet<>();



}
