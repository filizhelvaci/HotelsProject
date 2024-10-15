package com.flz.service;

import com.flz.mapper.IReservationMapper;
import com.flz.model.entity.Reservation;
import com.flz.model.request.ReservationCreateRequest;
import com.flz.repository.ReservationRepository;
import com.flz.utils.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends ServiceManager<Reservation,Long> {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        super(reservationRepository);
        this.reservationRepository = reservationRepository;
    }

    public Boolean createReservation(ReservationCreateRequest dto) {

        save(IReservationMapper.INSTANCE.fromUsertoReservation(dto));
        return true;
    }



}
