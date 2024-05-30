package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.RoomType;
import com.flz.model.Rooms;
import com.flz.repository.IRoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomTypeService {

    @Autowired
    IRoomTypeRepository IroomTypeRepository;

    public List<RoomType> getAllRoomType() {
        return IroomTypeRepository.findAll();
    }

    public ResponseEntity<RoomType> getByRoomType(Long id)throws ResourceNotFoundException {

        RoomType roomType=IroomTypeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Room type not found ID : "+id));
        return ResponseEntity.ok().body(roomType);
    }

    public RoomType saveRoomType(RoomType roomType){
        if(IroomTypeRepository.findById(roomType.getId()).isPresent())
            return null;
        return IroomTypeRepository.save(roomType);
    }

    public Map<String,Boolean> deleteRoomType(Long id)throws ResourceNotFoundException{
        RoomType roomType=IroomTypeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Room Type not found ID: "+id));
         IroomTypeRepository.deleteById(id);
        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted "+id+". Record ",Boolean.TRUE);
        return response;
    }

    public String deleteAllRoomType(){
        IroomTypeRepository.deleteAll();
        return "All Room Types deleted";
    }

    public ResponseEntity<RoomType> updateRoomType(Long id, RoomType roomType)throws ResourceNotFoundException{
      IroomTypeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Room Type not found ID : "+id));
        roomType.setId(id);
        return ResponseEntity.ok(IroomTypeRepository.save(roomType));
    }

}
