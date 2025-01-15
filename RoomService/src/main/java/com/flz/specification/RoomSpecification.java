package com.flz.specification;

import com.flz.model.entity.RoomEntity;
import com.flz.model.enums.RoomStatus;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {

    private RoomSpecification() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Specification<RoomEntity> hasNumber(Integer number) {
        return (root, query, criteriaBuilder) ->
                number == null ? null : criteriaBuilder.equal(root.get("number"), number);
    }

    public static Specification<RoomEntity> hasFloor(Integer floor) {
        return (root, query, criteriaBuilder) ->
                floor == null ? null : criteriaBuilder.equal(root.get("floor"), floor);
    }

    public static Specification<RoomEntity> hasStatus(RoomStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<RoomEntity> hasRoomTypeId(Long typeId) {
        return (root, query, criteriaBuilder) ->
                typeId == null ? null : criteriaBuilder.equal(root.join("type").get("id"), typeId);
    }

}
