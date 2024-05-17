package com.flz.controller;

import com.flz.model.Hotel;
import com.flz.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {


    @Autowired
    HotelService hotelService;

    //    http://localhost:8086/hotel/hello
    @GetMapping("/hello")
    public String hello() {
        return "Room Service Hotel'den --Hello-- ";
    }

    //    http://localhost:8086/hotel/getall
    @GetMapping("/getall")
    public List<Hotel> getHotel(){

        return hotelService.getAllHotel();
    }

    //    http://localhost:8086/hotel/getone/id
    @GetMapping("/getone/{id}")
    public Hotel getHotel(@PathVariable(value = "id") Long id){

        return hotelService.getByHotel(id);
    }

    //    http://localhost:8086/hotel/save
    @PostMapping("/save")
    public Hotel saveHotel(@RequestBody Hotel hotel){

        return hotelService.saveHotel(hotel);
    }

    // PUT - UPDATE
    // http://localhost:8086/hotel/update
    @PutMapping ("/update/{id}")
    public Hotel updateHotel(@PathVariable(value="id") Long id,
                                   @RequestBody Hotel hotel) {

        Hotel newHotel= hotelService.getByHotel(id);

        if(newHotel != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
            newHotel.setId(id);
            newHotel.setHotelName(hotel.getHotelName());
            newHotel.setHotelType(hotel.getHotelType());
            newHotel.setRoomCount(hotel.getRoomCount());
            newHotel.setDescription(hotel.getDescription());
            newHotel.setRooms(hotel.getRooms());
            //Hotel özellikleri list şeklinde nasıl olacak
            newHotel.setHotelProperties(hotel.getHotelProperties());
            return hotelService.updateHotel(newHotel);
        }
        return null;
    }


    // DELETE - DELETE
    // http://localhost:8086/hotel/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteHotel(@PathVariable (value = "id") Long id) {

        return hotelService.deleteHotel(id);
    }


}
