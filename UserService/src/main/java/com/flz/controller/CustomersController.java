package com.flz.controller;

import com.flz.constant.EndPoint;
import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.response.DoRegisterResponseCustomerDto;
import com.flz.service.CustomersService;
import com.flz.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomersController {


    // ****************** @AutoWired *************** //
    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }


    // *******************    ********************* //
    UsersService usersService;



    // -----------------------------REGISTER----------------------------------------------//

    //    http://localhost:8083/customers/register
    @PostMapping(EndPoint.REGISTER)
    public ResponseEntity<DoRegisterResponseCustomerDto> doRegister(@RequestBody DoCustomerRegisterRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doRegisterCustomer(dto));
    }

    // ------------------------------LOGIN-------------------------------------------------//
    //    http://localhost:8083/customers/login
    @PostMapping(EndPoint.LOGIN)
    public ResponseEntity<String> doLogin(@RequestBody DoLoginRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doLoginCustomer(dto));
    }

// 888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888 //
//
//    //    http://localhost:8083/customers/getall
//    @GetMapping("/getall")
//    public List<Customers> getCustomers(){
//
//        return customersService.findAll();
//    }
//
//
//    //    http://localhost:8083/customers/getone/
//    @GetMapping("/getone/{id}")
//    public ResponseEntity<Customers> getCustomer(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//
//        return ResponseEntity.ok(customersService.findById(id).get());
//    }
//
//
//    //    http://localhost:8083/customers/save
//    @PostMapping("/save")
//    public Customers saveCustomer(@RequestBody Customers customer){
//
//        return customersService.save(customer);
//    }
//
//    // http://localhost:8083/customers/update/
//    @PutMapping ("/update/{id}")
//    public ResponseEntity<Customers> updateCustomer(@PathVariable(value="id") Long id,
//                                    @RequestBody Customers customers) throws ResourceNotFoundException
//    {
//
//        return ResponseEntity.ok(customersService.update(customers));
//    }
//
//
//    // DELETE - DELETE
//    // http://localhost:8083/customers/delete/
//    @DeleteMapping ("/delete/{id}")
//    public void deleteCustomer(@PathVariable (value = "id") Long id) throws ResourceNotFoundException {
//        customersService.deleteById(id);
//    }
//


}
