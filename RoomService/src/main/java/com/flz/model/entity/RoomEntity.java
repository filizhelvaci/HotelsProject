package com.flz.model.entity;

import com.flz.model.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@Table(name = "rs_room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "floor")
    private String floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RoomStatus status;

    @Column(name = "type_id")
    private Long typeId;

    /**----------------------------------------------------------------
      RoomEntity      RoomTypeEntity
          1                 1
    */
    @OneToOne
    @JoinColumn(name = "type_id")
    private RoomTypeEntity type;


}