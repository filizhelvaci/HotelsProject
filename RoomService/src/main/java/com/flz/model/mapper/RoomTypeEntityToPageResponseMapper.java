package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface RoomTypeEntityToPageResponseMapper extends BaseMapper<RoomTypeEntity, RoomTypesResponse> {

    RoomTypeEntityToPageResponseMapper INSTANCE = Mappers.getMapper(RoomTypeEntityToPageResponseMapper.class);

    default Page<RoomTypesResponse> map(Page<RoomTypeEntity> roomTypeEntities) {
        return roomTypeEntities.map(this::map);
    }
}