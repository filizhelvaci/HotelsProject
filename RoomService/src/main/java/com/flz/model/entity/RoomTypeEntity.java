package com.flz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rs_room_type")
public class RoomTypeEntity extends BaseEntity {

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

   /** ----------------------------------------------------------------
      RoomTypeEntity   AssetEntity
            M              M
   */
    @ManyToMany
    @JoinTable(
            name = "rs_room_type_asset",
            joinColumns = @JoinColumn(name = "room_type_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    private List<AssetEntity> assets;

}
