package com.flz.service;

import com.flz.dto.request.DoReservationRequestDto;
import com.flz.mapper.IReservationMapper;
import com.flz.model.Reservation;
import com.flz.repository.IReservationRepository;
import com.flz.utils.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends ServiceManager<Reservation,Long> {

    // ****************** @AutoWired *************** //
    private final IReservationRepository IreservationRepository;

    public ReservationService(IReservationRepository IreservationRepository) {
        super(IreservationRepository);
        this.IreservationRepository= IreservationRepository;
    }
    // ************************************************* //

    public Boolean createReservation(DoReservationRequestDto dto){

        return null;
    }


}
