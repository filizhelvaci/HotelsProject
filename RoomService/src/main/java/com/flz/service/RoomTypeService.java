package com.flz.service;

import com.flz.model.RoomType;
import com.flz.repository.IRoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {

    @Autowired
    IRoomTypeRepository IroomTypeRepository;

    public List<RoomType> getAllRoomType() {
        return IroomTypeRepository.findAll();
    }

    public RoomType getByRoomType(Long id) {
        return IroomTypeRepository.findById(id).get();
    }

    public RoomType saveRoomType(RoomType roomType){
        return IroomTypeRepository.save(roomType);
    }

    public String deleteRoomType(Long id){
        // silme işleminde bi onay daha istenebilir x kişisini silmek istediğinizden emin misiniz? gibi
        IroomTypeRepository.deleteById(id);
        return "RoomType = "+id+ "deleted ";
    }

    public String deleteAllRoomType(){
        IroomTypeRepository.deleteAll();
        return "All Room Types deleted";
    }

    public RoomType updateRoomType(RoomType roomType){

        return IroomTypeRepository.save(roomType);
    }
}
