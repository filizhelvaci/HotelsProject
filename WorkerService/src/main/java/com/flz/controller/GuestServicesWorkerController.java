package com.flz.controller;

import com.flz.model.Users;
import com.flz.service.GuestServicesWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class GuestServicesWorkerController {

    @Autowired
    GuestServicesWorkerService guestServicesWorkerService;

    //    http://localhost:8085/users/all
    @GetMapping("/users/all")
    public List<Users> getUsers(){
          return guestServicesWorkerService.getAllUsers();
     }

/*
     @GetMapping
    public Users getUser(){
        return guestServicesWorkerService.
     }*/
}
