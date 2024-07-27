package com.flz.mapper;


import com.flz.dto.request.DoReservationRequestDto;
import com.flz.dto.request.DoRoomReservationRequestDto;
import com.flz.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IReservationMapper {

    IReservationMapper INSTANCE= Mappers.getMapper(IReservationMapper.class);
    Reservation fromUsertoReservation(final DoReservationRequestDto dto);

    Reservation fromRoomtoReservation(final DoRoomReservationRequestDto dto);


}

