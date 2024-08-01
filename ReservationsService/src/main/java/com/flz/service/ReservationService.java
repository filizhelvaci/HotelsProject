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

    public String createReservation(DoReservationRequestDto dto){


        Reservation reservation= IReservationMapper.INSTANCE.fromUsertoReservation(dto);

        Reservation savedReservation=IreservationRepository.save(reservation);


        return "Reservasyon yapıldı.";
    }


}
