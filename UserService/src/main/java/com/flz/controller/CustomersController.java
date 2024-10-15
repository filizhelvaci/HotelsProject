package com.flz.controller;

import com.flz.constant.EndPoint;
import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.response.DoRegisterResponseCustomerDto;
import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Customers;
import com.flz.service.CustomersService;
import com.flz.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    UsersService usersService;

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }


    @PostMapping(EndPoint.REGISTER)
    public ResponseEntity<DoRegisterResponseCustomerDto> doRegister(@RequestBody DoCustomerRegisterRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doRegisterCustomer(dto));
    }

    @PostMapping(EndPoint.LOGIN)
    public ResponseEntity<String> doLogin(@RequestBody DoLoginRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doLoginCustomer(dto));
    }


    @GetMapping("/getall")
    public ResponseEntity<List<Customers>> getCustomers(){

        return ResponseEntity.ok(customersService.findAll());
    }


    @GetMapping("/getone/{id}")
    public ResponseEntity<Customers> getCustomer(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        return ResponseEntity.ok(customersService.findById(id).get());
    }


}
