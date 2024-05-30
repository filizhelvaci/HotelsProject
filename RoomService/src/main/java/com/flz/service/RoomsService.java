package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.RoomType;
import com.flz.model.Rooms;
import com.flz.repository.IRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomsService {

    @Autowired
    IRoomsRepository IroomsRepository;

    public List<Rooms> getAllRoom() {
        return IroomsRepository.findAll();
    }


    public ResponseEntity<Rooms> getByRoom(Long id)throws ResourceNotFoundException {

        Rooms rooms=IroomsRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found ID"+id));
        return ResponseEntity.ok().body(rooms);
    }

    public Rooms saveRoom(Rooms rooms){
        if(IroomsRepository.findById(rooms.getId()).isPresent())
            return null;
        return IroomsRepository.save(rooms);
    }


    public Map<String,Boolean> deleteRoom(Long id)throws ResourceNotFoundException{

        // silme işleminde bi onay daha istenebilir x kişisini silmek istediğinizden emin misiniz? gibi
        Rooms rooms=IroomsRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Room not found ID : "+id));
        IroomsRepository.deleteById(id);
        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted "+id +". Record",Boolean.TRUE);
        return response;
    }


    public String deleteAllRoomType(){
        IroomsRepository.deleteAll();
        return "All Room Types deleted";
    }

    public ResponseEntity<Rooms> updateRoom(Long id,Rooms room)throws ResourceNotFoundException{
       IroomsRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("room not found ID : "+id));

        room.setId(id);
        return ResponseEntity.ok(IroomsRepository.save(room));
    }

}
