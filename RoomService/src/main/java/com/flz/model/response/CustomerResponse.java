package com.flz.model.response;

import com.flz.model.enums.RoomStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CustomerResponse {

    private String roomTypeName;
    private BigDecimal price;
    private Integer personCount;
    private Integer size;
    private Long roomTypeId;
    private List<Room> rooms;

    @Getter
    @Builder
    public static class Room {
        private Long id;
        private Integer number;
        private RoomStatus status; // empty, full, reserve
    }
}
