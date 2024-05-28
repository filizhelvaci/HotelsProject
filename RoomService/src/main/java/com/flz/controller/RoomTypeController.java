package com.flz.controller;

import com.flz.model.RoomType;
import com.flz.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public RoomType getRoomType(@PathVariable(value = "id") Long id){

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
    public RoomType updateRoomType(@PathVariable(value="id") Long id,
                                   @RequestBody RoomType roomType) {

        RoomType roomType1= roomTypeService.getByRoomType(id);

        if(roomType1 != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
            roomType1.setId(id);
            roomType1.setRoomCount(roomType.getRoomCount());
            roomType1.setRoomTypeName(roomType.getRoomTypeName());
            roomType1.setRoomCount(roomType1.getRoomCount());
            return roomTypeService.updateRoomType(roomType1);
        }
        return null;
    }


    // DELETE - DELETEALL
    // http://localhost:8082/roomtypes/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteRoomType(@PathVariable (value = "id") Long id) {

        return roomTypeService.deleteRoomType(id);
    }

    // http://localhost:8082/roomtypes/deleteAll/
    @DeleteMapping ("/deleteAll")
    public String deleteAllRoomType() {

        return roomTypeService.deleteAllRoomType();
    }

}
