package com.flz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//    http://localhost:8086
@RestController
@RequestMapping
public class HelloController {

    //    http://localhost:8086
    @GetMapping("/hello")
    public String hello() {
        return "Room Service'ten --Hello-- ";
    }


    //    http://localhost:8086/info
    @GetMapping("/info")
    public String info() {
        return "INFO: Room Service ";
    }
}