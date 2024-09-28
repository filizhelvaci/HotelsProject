package com.flz.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter

@Entity
@Table(name = "rs_room_type_asset")
public class RoomTypeAssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_type_id")
    private Long roomTypeId;

    @Column(name = "asset_id")
    private Long assetId;

}
