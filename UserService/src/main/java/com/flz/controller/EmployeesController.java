package com.flz.controller;

import com.flz.constant.EndPoint;
import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.response.DoRegisterResponseDto;
import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Employees;
import com.flz.service.EmployeesService;
import com.flz.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    UsersService usersService;

    // -----------------------------REGISTER----------------------------------------------//

    //    http://localhost:8083/employees/register
    @PostMapping(EndPoint.REGISTER)
    public ResponseEntity<DoRegisterResponseDto> doRegister(@RequestBody DoEmployeeRegisterRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doRegisterEmployee(dto));
    }

    // ------------------------------LOGIN-------------------------------------------------//
    //    http://localhost:8083/employees/login
    @PostMapping(EndPoint.LOGIN)
    public ResponseEntity<String> doLogin(@RequestBody DoLoginRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doLoginEmployee(dto));
    }

// 888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888 //
//
//
//    //    http://localhost:8083/employees/getall
//    @GetMapping("/getall")
//    public List<Employees> getEmployees(){
//
//        return employeesService.findAll();
//     }
//
//
//    //    http://localhost:8083/employees/getone/
//     @GetMapping("/getone/{id}")
//    public ResponseEntity<Employees> getEmployee(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
//
//        return ResponseEntity.ok(employeesService.findById(id).get());
//     }
//
//    //    http://localhost:8083/employees/save
//    @PostMapping("/save")
//    public Employees saveEmployee(@RequestBody Employees employee){
//
//        return employeesService.save(employee);
//    }
//
//
//
//// FIXME update
//    // http://localhost:8083/employee/update/
//    @PutMapping ("/update/{id}")
//    public ResponseEntity<Employees> updateEmployee(@PathVariable(value="id") Long id,
//                                 @RequestBody Employees employees)throws ResourceNotFoundException {
//
//       return ResponseEntity.ok(employeesService.update(employees));
//    }
//
//
//    // http://localhost:8083/employee/delete/
//    @DeleteMapping ("/delete/{id}")
//    public void deleteEmployee(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
//        employeesService.deleteById(id);
//    }

}
