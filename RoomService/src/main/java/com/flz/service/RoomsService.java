package com.flz.service;

import com.flz.model.RoomType;
import com.flz.model.Rooms;
import com.flz.repository.IRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {

    @Autowired
    IRoomsRepository IroomsRepository;

    public List<Rooms> getAllRoom() {
        return IroomsRepository.findAll();
    }

    public Rooms getByRoom(Long id) {
        return IroomsRepository.findById(id).get();
    }

    public Rooms saveRoom(Rooms rooms){
        return IroomsRepository.save(rooms);
    }

    public String deleteRoom(Long id){
        // silme işleminde bi onay daha istenebilir x kişisini silmek istediğinizden emin misiniz? gibi
        IroomsRepository.deleteById(id);
        return "Room = "+id+ "deleted ";
    }

    public String deleteAllRoomType(){
        IroomsRepository.deleteAll();
        return "All Room Types deleted";
    }

    public Rooms updateRoom(Rooms room){

        return IroomsRepository.save(room);
    }
}
