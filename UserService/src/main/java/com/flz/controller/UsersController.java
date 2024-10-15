package com.flz.controller;

import com.flz.model.Users;
import com.flz.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/getall")
    public List<Users> getUsers(){

        return usersService.findAll();
    }

    @GetMapping("/getone/{id}")
    public Optional<Users> getUser(@PathVariable(value = "id") Long id) {

        return usersService.findById(id);
    }

}
