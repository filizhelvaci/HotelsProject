package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="RS_ROOM_TYPE_ASSET")
public class RoomTypeAssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="ROOM_TYPE_ID",nullable = false)
    private Long roomTypeId;

    @Column(name="ASEET_ID",nullable = false)
    private Long assetId;

}
