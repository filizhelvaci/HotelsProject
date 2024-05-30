package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.RoomType;
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
    public List<RoomType> getRoomType(){

        return roomTypeService.getAllRoomType();
    }

    //    http://localhost:8082/roomtypes/getone/id

    @GetMapping("/getone/{id}")
    public ResponseEntity<RoomType> getRoomType(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return roomTypeService.getByRoomType(id);
    }

    // SAVE - INSERT
    //    http://localhost:8082/roomtypes/save
    @PostMapping("/save")
    public RoomType saveRoomType(@RequestBody RoomType roomType){

        return roomTypeService.saveRoomType(roomType);
    }

    // PUT - UPDATE
    // http://localhost:8082/roomtypes/update
    @PutMapping ("/update/{id}")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable(value="id") Long id,
                                   @RequestBody RoomType roomType)throws ResourceNotFoundException {

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
