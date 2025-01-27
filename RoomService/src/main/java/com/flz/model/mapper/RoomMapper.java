package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomEntity toRoomEntity(final RoomCreateRequest roomCreateRequest);

    RoomResponse toRoomResponse(final RoomEntity roomEntity);

    RoomsSummaryResponse toRoomsSummaryResponse(final RoomEntity roomEntity);

    RoomsResponse toRoomsResponse(final RoomEntity roomEntity);
}
