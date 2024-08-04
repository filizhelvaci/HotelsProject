package com.flz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservationsApp {
    public static void main(String[] args) {

        SpringApplication.run(ReservationsApp.class, args);
    }
}