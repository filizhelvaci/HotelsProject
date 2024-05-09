package com.flz.controller;

import com.flz.model.Users;
import com.flz.service.GuestServicesWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    //    http://localhost:8085/users/id
     @GetMapping("/users/{id}")
    public Users getUser(@PathVariable (value = "id") Long id){

        return guestServicesWorkerService.getByUser(id);
     }
}
