package com.flz.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "rs_room_type")
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "person_count")
    private Integer personCount;

    @Column(name = "size")
    private Integer size;

    @Column(name = "description")
    private String description;

    //----------------------------------------------------------------
    //  RoomTypeEntity   AssetEntity
    //        M              M
    @ManyToMany
    @JoinTable(
            name = "rs_room_type_asset",
            joinColumns = @JoinColumn(name = "room_type_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    private List<AssetEntity> assets;

    //----------------------------------------------------------------
    // RoomTypeEntity  RoomEntity
    //      1               M
    @OneToMany(mappedBy = "roomTypeEntity", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<RoomEntity> roomEntities;

}