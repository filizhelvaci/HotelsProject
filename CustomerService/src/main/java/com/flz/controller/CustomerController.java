package com.flz.controller;

import com.flz.model.Customer;
import com.flz.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomersService customersService;

    //    http://localhost:8084/customers/getall
    @GetMapping("/getall")
    public List<Customer> getCustomers(){

        return customersService.getAllCustomers();
    }


    //    http://localhost:8084/customers/getone/id
    @GetMapping("/getone/{id}")
    public Customer getCustomer(@PathVariable(value = "id") Long id){

        return customersService.getByCustomer(id);
    }

    //    http://localhost:8084/customers/save
    @PostMapping("/save")
    public Customer saveCustomer(@RequestBody Customer customer){

        return customersService.saveCustomer(customer);
    }

    // PUT - UPDATE
    // http://localhost:8084/customers/update
    @PutMapping ("/update/{id}")
    public Customer updateCustomer(@PathVariable(value="id") Long id,
                            @RequestBody Customer customer) {

        Customer customerInfo= customersService.getByCustomer(id);

        if(customerInfo != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
             customerInfo.setId(id);
             customerInfo.setCity(customer.getCity());
             customerInfo.setBirthDate(customer.getBirthDate());
             customerInfo.setEmail(customer.getEmail());
             customerInfo.setEnterDate(customer.getEnterDate());
             customerInfo.setExitDate(customer.getExitDate());
             customerInfo.setIDnumber(customer.getIDnumber());
             customerInfo.setName(customer.getName());
             customerInfo.setLastName(customer.getLastName());
             customerInfo.setNationality(customer.getNationality());
             customerInfo.setPhoneNumber(customer.getPhoneNumber());
             customerInfo.setRoomNumber(customer.getRoomNumber());
             customerInfo.setUserId(customer.getUserId());
            return customersService.updateCustomer(customerInfo);
        }
        return null;
    }


    // DELETE - DELETE
    // http://localhost:8084/customers/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteCustomer(@PathVariable (value = "id") Long id) {

        return customersService.deleteCutomer(id);
    }

}
