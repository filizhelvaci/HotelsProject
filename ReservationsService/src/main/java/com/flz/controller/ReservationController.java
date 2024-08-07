package com.flz.controller;

import com.flz.dto.request.DoReservationRequestDto;
import com.flz.model.Reservation;
import com.flz.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    // ****************** @AutoWired *************** //
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // http://localhost:8081/reservation/hello
    @GetMapping("/hello")
    public String hello(){
        return "Reservation service hello said...";
    }

    // http://localhost:8081/reservation/create
    @PostMapping("/create")
    public ResponseEntity<Boolean> create (@RequestBody DoReservationRequestDto dto){
        reservationService.createReservation(dto);
        return ResponseEntity.ok(Boolean.TRUE);
    }


    //    http://localhost:8081/reservation/getall
    @GetMapping("/getall")
    public ResponseEntity<List<Reservation>> getAll(){

        return ResponseEntity.ok(reservationService.findAll());
    }

}
