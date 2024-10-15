package com.flz.controller;

import com.flz.model.entity.Reservation;
import com.flz.model.request.ReservationCreateRequest;
import com.flz.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Reservation service hello said...";
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody ReservationCreateRequest dto) {
        reservationService.createReservation(dto);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Reservation>> getAll(){

        return ResponseEntity.ok(reservationService.findAll());
    }

}
