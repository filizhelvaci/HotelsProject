package com.flz.controller;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.service.RoomTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping("/room-types")
    public ResponseEntity<List<RoomTypeResponse>> findAll() {
        final List<RoomTypeResponse> roomTypeResponses = roomTypeService.findAll();
        return ResponseEntity.ok(roomTypeResponses);
    }


    @GetMapping("/room-type/{id}")
    public ResponseEntity<RoomTypeResponse> findById(@PathVariable(value = "id") Long id) {
        RoomTypeResponse roomTypeResponse = roomTypeService.findById(id);
        return ResponseEntity.ok(roomTypeResponse);
    }


    @PostMapping("/room-type")
    public ResponseEntity<Void> create(@RequestBody @Valid RoomTypeCreateRequest roomTypeCreateRequest) {
        roomTypeService.create(roomTypeCreateRequest);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/room-type/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") @Positive Long id,
            @RequestBody @Valid RoomTypeUpdateRequest roomTypeUpdateRequest) {

        roomTypeService.update(id, roomTypeUpdateRequest);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/room-type/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        roomTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
