package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface RoomEntityToPageResponseMapper extends BaseMapper<RoomEntity, RoomsResponse> {

    RoomEntityToPageResponseMapper INSTANCE = Mappers.getMapper(RoomEntityToPageResponseMapper.class);

    RoomsResponse map(RoomEntity roomEntity);

    default Page<RoomsResponse> map(Page<RoomEntity> roomEntities) {
        return roomEntities.map(this::map);
    }

}
