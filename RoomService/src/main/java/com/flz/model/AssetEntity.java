package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="RS_ASSET")
public class AssetEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(name = "NAME", nullable = false, length = 50)
        private String name;

        @Column(name = "PRICE",nullable = false)
        private BigDecimal price;

        @Column(name = "IS_DEFAULT", nullable = false)
        private String isDefault;

}