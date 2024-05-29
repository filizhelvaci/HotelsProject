package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Hotel;
import com.flz.model.Rooms;
import com.flz.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {


    @Autowired
    HotelService hotelService;

    //    http://localhost:8082/hotel/hello
    @GetMapping("/hello")
    public String hello() {
        return "Room Service Hotel'den --Hello-- ";
    }

    //    http://localhost:8082/hotel/getall
    @GetMapping("/getall")
    public List<Hotel> getHotel(){

        return hotelService.getAllHotel();
    }

    //    http://localhost:8082/hotel/getone/id
    @GetMapping("/getone/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable(value = "id") Long id)throws ResourceNotFoundException{

        return hotelService.getByHotel(id);
    }

    //    http://localhost:8082/hotel/save
    @PostMapping("/save")
    public Hotel saveHotel(@RequestBody Hotel hotel){

        return hotelService.saveHotel(hotel);
    }

    // PUT - UPDATE
    // http://localhost:8082/hotel/update
    @PutMapping ("/update/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable(value="id") Long id,
                                   @RequestBody Hotel hotel)throws ResourceNotFoundException {

        return hotelService.updateHotel(id,hotel);


    }

    // DELETE - DELETE
    // http://localhost:8082/hotel/delete/
    @DeleteMapping ("/delete/{id}")
    public Map<String,Boolean> deleteHotel(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {

        return hotelService.deleteHotel(id);
    }


}
