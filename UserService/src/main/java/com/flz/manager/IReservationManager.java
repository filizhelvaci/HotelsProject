package com.flz.manager;

import com.flz.dto.request.DoReservationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="reservation-manager",
        url = "http://localhost:8081/reservation")
public interface IReservationManager {

    @PostMapping("/create")
    ResponseEntity<Boolean> create (@RequestBody DoReservationRequestDto dto);
}
