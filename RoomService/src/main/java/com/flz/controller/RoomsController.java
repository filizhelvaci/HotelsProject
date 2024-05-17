package com.flz.controller;

import com.flz.model.Rooms;
import com.flz.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Rooms")
public class RoomsController {


    @Autowired
    RoomsService roomsService;

    //    http://localhost:8086/rooms/hello
    @GetMapping("/hello")
    public String hello() {
        return "Room Service'ten --Hello-- ";
    }

    //    http://localhost:8086/rooms/getall
    @GetMapping("/getall")
    public List<Rooms> getRooms(){

        return roomsService.getAllRoom();
    }


    //    http://localhost:8086/rooms/getone/id
    @GetMapping("/getone/{id}")
    public Rooms getRoom(@PathVariable(value = "id") Long id){

        return roomsService.getByRoom(id);
    }

    //    http://localhost:8086/rooms/save
    @PostMapping("/save")
    public Rooms saveRoom(@RequestBody Rooms rooms){

        return roomsService.saveRoom(rooms);
    }

    // PUT - UPDATE
    // http://localhost:8086/rooms/update
    @PutMapping ("/update/{id}")
    public Rooms updateRoom(@PathVariable(value="id") Long id,
                                   @RequestBody Rooms room) {

        Rooms newRoom= roomsService.getByRoom(id);

        if(newRoom != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
            newRoom.setId(id);
            newRoom.setRoomNumber(room.getRoomNumber());
            //propersties collection olmalımı???????
            newRoom.setRoomProperties(room.getRoomProperties());
            newRoom.setM2(room.getM2());
            newRoom.setCountOfDoubleBed(room.getCountOfDoubleBed());
            newRoom.setCountOfSingleBed(room.getCountOfSingleBed());
            newRoom.setCountPerson(room.getCountPerson());
            newRoom.setDescription(room.getDescription());
            newRoom.setWhichfloor(room.getWhichfloor());
            newRoom.setPrice(room.getPrice());
            //isFull get ?
            newRoom.setFull(room.isFull());
            return roomsService.updateRoom(newRoom);
        }
        return null;
    }


    // DELETE - DELETE
    // http://localhost:8086/rooms/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteRoom(@PathVariable (value = "id") Long id) {

        return roomsService.deleteRoom(id);
    }

    // http://localhost:8086/rooms/deleteAll
    @DeleteMapping ("/deleteAll")
    public String deleteAllRoomType() {

        return roomsService.deleteAllRoomType();
    }




}
