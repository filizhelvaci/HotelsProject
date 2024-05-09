package com.flz.controller;

import com.flz.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel/users")
public class UsersController {

    private UsersService usersService;


    //    http://localhost:8084
    @GetMapping("/getUser")
    public String getUser() {

        return "tek user getirir";
    }
}
