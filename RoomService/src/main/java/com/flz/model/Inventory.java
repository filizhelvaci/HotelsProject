package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.*;

@SuperBuilder // bir sınıftan nesne türetmek için
@Data //set get metotlarını otomatik tanımlar
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    // Inventory       AdditionalFeature
//    //   1                    m
//    @OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    private Set<AdditionalFeature> additionalFeature = new HashSet<>();

    private Long aFId;

    private Long availableQuantity; // mevcut miktar
    private Long amountUsed; // Kullanılan miktar
    private String place;

}
