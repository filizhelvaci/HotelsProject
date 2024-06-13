package com.flz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//    http://localhost:8083
@RestController
@RequestMapping
public class HelloController {

    //    http://localhost:8083/hello
    @GetMapping("/hello")
    public String hello() {
        return "Users Service'ten --Hello-- ";
    }


    //    http://localhost:8083/info
    @GetMapping("/info")
    public String info() {
        return "INFO: Users Service information ";
    }
}