package com.flz.repository;

import com.flz.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {


   // Optional<Reservation> findByRoomIdAndAndStartDateBetweenAndEndDate();
}
