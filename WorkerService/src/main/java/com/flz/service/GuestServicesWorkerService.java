package com.flz.service;

import com.flz.model.Users;
import com.flz.repository.GuestServicesWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServicesWorkerService {

    @Autowired
    GuestServicesWorkerRepository guestServicesWorkerRepository;

    public List<Users> getAllUsers(){
        return guestServicesWorkerRepository.findAll();
    }
/*
    public Users getByUser(Long id){
        return guestServicesWorkerRepository.findById(Long id);
    }*/
}
