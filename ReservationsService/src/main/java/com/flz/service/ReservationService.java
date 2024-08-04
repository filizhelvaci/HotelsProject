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


        save(IReservationMapper.INSTANCE.fromUsertoReservation(dto));

       // Reservation savedReservation=IreservationRepository.save(reservation);
/*
        Reservation reservation=new Reservation();
        reservation.setEMail(dto.getEMail());
        reservation.setName(dto.getName());
        reservation.setLastname(dto.getLastName());
        reservation.setUserType(dto.getUserType());
        save(reservation); */
/*
        Reservation reservation1=Reservation.builder()
                .name(dto.getName())
                .lastname(dto.getLastName())
                .eMail(dto.getEMail())
                .userType(dto.getUserType())
                .build();
        save(reservation1);
*/

       /* save(Reservation.builder()
                .name(dto.getName())
                .lastname(dto.getLastName())
                .eMail(dto.getEMail())
                .userType(dto.getUserType())
                .build());
*/
        return true;
    }



}
