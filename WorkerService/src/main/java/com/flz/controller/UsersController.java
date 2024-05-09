package com.flz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guests")
public class UsersController {

    @GetMapping("/info/{id}")
    public String getUser(@PathVariable(name="id") Long id) {

        return "guest id : "+ id;
    }
}
