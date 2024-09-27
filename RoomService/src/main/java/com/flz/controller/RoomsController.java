package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.RoomEntity;
import com.flz.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class RoomsController {


    @Autowired
    RoomsService roomsService;

    //    http://localhost:8082/rooms/hello
    @GetMapping("/hello")
    public String hello() {
        return "Room Service'ten --Hello-- ";
    }

    //    http://localhost:8082/Rooms/getall
    @GetMapping("/getall")
    public List<RoomEntity> getRooms(){

        return roomsService.getAllRoom();
    }


    //    http://localhost:8082/rooms/getone/id
    @GetMapping("/getone/{id}")
    public ResponseEntity<RoomEntity> getRoom(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return roomsService.getByRoom(id);
    }

    //    http://localhost:8082/rooms/save
    @PostMapping("/save")
    public RoomEntity saveRoom(@RequestBody RoomEntity rooms){

        return roomsService.saveRoom(rooms);
    }

    // PUT - UPDATE
    // http://localhost:8082/rooms/update
    @PutMapping ("/update/{id}")
    public ResponseEntity<RoomEntity> updateRoom(@PathVariable(value="id") Long id,
                                                 @RequestBody RoomEntity room)throws ResourceNotFoundException {

    return roomsService.updateRoom(id,room);
    }


    // DELETE - DELETE
    // http://localhost:8082/rooms/delete/
    @DeleteMapping ("/delete/{id}")
    public Map<String,Boolean> deleteRoom(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {

        return roomsService.deleteRoom(id);
    }


    // http://localhost:8082/rooms/deleteAll
    @DeleteMapping ("/deleteAll")
    public String deleteAllRoomType() {

        return roomsService.deleteAllRoomType();
    }

//    @PutMapping("/{id}/feature/{addFeaId}")
//    public ResponseEntity<Rooms> addFeature(@PathVariable Long id, @PathVariable Long addFeaId) {
//        Rooms rooms = RoomsService.AdditionalFeatureAdd(id,addFeaId);
//        return ResponseEntity.ok(rooms);
//    }
//


}
