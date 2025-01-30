package com.flz.model.response;

import com.flz.model.enums.RoomStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomsResponse {

    private Long id;
    private Integer number;
    private Integer floor;
    private RoomStatus status;
    private Type type;

    @Getter
    @Builder
    public static class Type {
        private Long id;
        private String name;
    }
}
