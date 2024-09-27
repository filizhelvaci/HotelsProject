package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.RoomTypeEntity;
import com.flz.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {

    @Autowired
    RoomTypeService roomTypeService;

    //    http://localhost:8082/roomtypes/hello
    @GetMapping("/hello")
    public String hello() {
        return "Room Service'ten --Hello-- ";
    }

    // GET -GETALL
    //    http://localhost:8082/roomtypes/getall
    @GetMapping("/getall")
    public List<RoomTypeEntity> getRoomType(){

        return roomTypeService.getAllRoomType();
    }

    //    http://localhost:8082/roomtypes/getone/id

    @GetMapping("/getone/{id}")
    public ResponseEntity<RoomTypeEntity> getRoomType(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return roomTypeService.getByRoomType(id);
    }

    // SAVE - INSERT
    //    http://localhost:8082/roomtypes/save
    @PostMapping("/save")
    public RoomTypeEntity saveRoomType(@RequestBody RoomTypeEntity roomType){

        return roomTypeService.saveRoomType(roomType);
    }

    // PUT - UPDATE
    // http://localhost:8082/roomtypes/update
    @PutMapping ("/update/{id}")
    public ResponseEntity<RoomTypeEntity> updateRoomType(@PathVariable(value="id") Long id,
                                                         @RequestBody RoomTypeEntity roomType)throws ResourceNotFoundException {

        return roomTypeService.updateRoomType(id,roomType);
    }


    // DELETE - DELETEALL
    // http://localhost:8082/roomtypes/delete/
    @DeleteMapping ("/delete/{id}")
    public Map<String,Boolean> deleteRoomType(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {

        return roomTypeService.deleteRoomType(id);
    }

    // http://localhost:8082/roomtypes/deleteAll/
    @DeleteMapping ("/deleteAll")
    public String deleteAllRoomType() {

        return roomTypeService.deleteAllRoomType();
    }

}
