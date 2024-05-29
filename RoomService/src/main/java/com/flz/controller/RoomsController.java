package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Rooms;
import com.flz.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Rooms")
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
    public List<Rooms> getRooms(){

        return roomsService.getAllRoom();
    }


    //    http://localhost:8082/rooms/getone/id
    @GetMapping("/getone/{id}")
    public ResponseEntity<Rooms> getRoom(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return roomsService.getByRoom(id);
    }

    //    http://localhost:8082/rooms/save
    @PostMapping("/save")
    public Rooms saveRoom(@RequestBody Rooms rooms){

        return roomsService.saveRoom(rooms);
    }

    // PUT - UPDATE
    // http://localhost:8082/rooms/update
    @PutMapping ("/update/{id}")
    public ResponseEntity<Rooms> updateRoom(@PathVariable(value="id") Long id,
                                   @RequestBody Rooms room)throws ResourceNotFoundException {

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




}
