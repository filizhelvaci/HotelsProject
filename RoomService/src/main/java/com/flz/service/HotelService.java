package com.flz.service;

import com.flz.model.Hotel;
import com.flz.repository.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    IHotelRepository IhotelRepository;

    public List<Hotel> getAllHotel() {
        return IhotelRepository.findAll();
    }

    public Hotel getByHotel(Long id) {
        return IhotelRepository.findById(id).get();
    }

    public Hotel saveHotel(Hotel hotel){
        return IhotelRepository.save(hotel);
    }

    public String deleteHotel(Long id){
        // silme işleminde bi onay daha istenebilir x kişisini silmek istediğinizden emin misiniz? gibi
        IhotelRepository.deleteById(id);
        return "Hotel = "+id+ "deleted ";
    }

    public Hotel updateHotel(Hotel hotel){

        return IhotelRepository.save(hotel);
    }
}
