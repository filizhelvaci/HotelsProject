package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Hotel;
import com.flz.repository.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {

    @Autowired
    IHotelRepository IhotelRepository;

    public List<Hotel> getAllHotel() {
        return IhotelRepository.findAll();
    }

    public ResponseEntity<Hotel> getByHotel(Long id)throws ResourceNotFoundException {
        Hotel hotel=IhotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found ID :"+id));
        return ResponseEntity.ok().body(hotel);
    }

    public Hotel saveHotel(Hotel hotel){
        if(IhotelRepository.findById(hotel.getId()).isPresent())
            return null;
        return IhotelRepository.save(hotel);
    }

    public Map<String,Boolean> deleteHotel(Long id)throws ResourceNotFoundException{

        Hotel hotel=IhotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found ID : "+id));
        IhotelRepository.deleteById(id);
        Map<String,Boolean> response=new HashMap<>();
        response.put(id+".record Deleted",Boolean.TRUE);
        return response;
    }

    public ResponseEntity<Hotel> updateHotel(Long id,Hotel hotel)throws ResourceNotFoundException{
       IhotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("hotel not found ID: "+id));
        hotel.setId(id);
        return ResponseEntity.ok(IhotelRepository.save(hotel));
    }

}
