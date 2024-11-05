package com.flz.model.entity;

import com.flz.model.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rs_room")
public class RoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "floor")
    private Integer floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RoomStatus status;

    /**----------------------------------------------------------------
      RoomEntity      RoomTypeEntity
          1                 1
    */
    @OneToOne
    @JoinColumn(name = "type_id")
    private RoomTypeEntity type;


}
