package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Customers;
import com.flz.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    CustomersService customersService;

    //    http://localhost:8084/customers/getall
    @GetMapping("/getall")
    public List<Customers> getCustomers(){

        return customersService.getAllCustomers();
    }


    //    http://localhost:8084/customers/getone/id
    @GetMapping("/getone/{id}")
    public ResponseEntity<Customers> getCustomer(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        return customersService.getByCustomer(id);
    }


    //    http://localhost:8084/customers/save
    @PostMapping("/save")
    public Customers saveCustomer(@RequestBody Customers customer){

        return customersService.saveCustomer(customer);
    }

    // PUT - UPDATE
    // http://localhost:8084/customers/update
    @PutMapping ("/update/{id}")
    public ResponseEntity<Customers> updateCustomer(@PathVariable(value="id") Long id,
                                    @RequestBody Customers customers) throws ResourceNotFoundException
    {

        return customersService.updateCustomer(id, customers);
    }


    // DELETE - DELETE
    // http://localhost:8084/customers/delete/
    @DeleteMapping ("/delete/{id}")
    public Map<String,Boolean> deleteCustomer(@PathVariable (value = "id") Long id) throws ResourceNotFoundException {

        return customersService.deleteCustomer(id);
    }



}
