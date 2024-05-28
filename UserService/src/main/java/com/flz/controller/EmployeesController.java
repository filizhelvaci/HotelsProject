package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Employees;
import com.flz.service.EmployeesService;
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

    //    http://localhost:8081/employees/getall
    @GetMapping("/getall")
    public List<Employees> getEmployees(){

        return employeesService.getAllEmployees();
     }


    //    http://localhost:8081/employees/getone/
     @GetMapping("/getone/{id}")
    public ResponseEntity<Employees> getEmployee(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {

        return employeesService.getByEmployee(id);
     }

    //    http://localhost:8081/employees/save
    @PostMapping("/save")
    public Employees saveEmployee(@RequestBody Employees employee){

        return employeesService.saveEmployee(employee);
    }


    // http://localhost:8081/employee/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<Employees> updateEmployee(@PathVariable(value="id") Long id,
                                 @RequestBody Employees employees)throws ResourceNotFoundException {

       return employeesService.updateEmployee(id,employees);
    }


    // http://localhost:8081/employee/delete/
    @DeleteMapping ("/delete/{id}")
    public Map<String,Boolean> deleteEmployee(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {

        return employeesService.deleteEmployee(id);
    }

}
