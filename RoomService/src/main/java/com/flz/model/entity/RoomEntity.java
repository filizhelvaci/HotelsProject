package com.flz.model.entity;

import com.flz.model.enums.RoomStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * ----------------------------------------------------------------
     * RoomEntity      RoomTypeEntity
     * 1                 1
     */
    @OneToOne
    @JoinColumn(name = "room_type_id")
    private RoomTypeEntity type;

    public void update(Integer number, Integer floor, RoomStatus status) {
        this.number = number;
        this.floor = floor;
        this.status = status;
    }


    public static Specification<RoomEntity> generateSpecification(Integer number,
                                                                  Integer floor,
                                                                  RoomStatus status,
                                                                  Long typeId) {

        return Specification.where(hasNumber(number))
                .and(hasFloor(floor))
                .and(hasStatus(status))
                .and(hasRoomTypeId(typeId));
    }

    private static Specification<RoomEntity> hasNumber(Integer number) {

        if (number == null) {
            return null;
        }

        return (Root<RoomEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("number"), number);
    }

    private static Specification<RoomEntity> hasFloor(Integer floor) {

        if (floor == null) {
            return null;
        }

        return (Root<RoomEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("floor"), floor);
    }

    private static Specification<RoomEntity> hasStatus(RoomStatus status) {

        if (status == null) {
            return null;
        }

        return (Root<RoomEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<RoomEntity> hasRoomTypeId(Long typeId) {

        if (typeId == null) {
            return null;
        }

        return (Root<RoomEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.join("type").get("id"), typeId);
    }
}
