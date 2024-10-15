package com.flz.mapper;

import com.flz.model.entity.Reservation;
import com.flz.model.request.ReservationCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IReservationMapper {
    IReservationMapper INSTANCE= Mappers.getMapper(IReservationMapper.class);

    Reservation fromUsertoReservation(final ReservationCreateRequest dto);
}

