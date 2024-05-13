package com.flz.controller;

import com.flz.model.Employees;
import com.flz.model.Users;
import com.flz.repository.ManegementWorkerRepository;
import com.flz.service.ManegementWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//
@RestController
@RequestMapping("/admin")
public class ManagementWorkerController {

    @Autowired
    ManegementWorkerService manegementWorkerService;

    //    http://localhost:8085/admin/all
    @GetMapping("/all")
    public List<Employees> getEmployees(){

        return manegementWorkerService.getAllEmployees();
    }


    //    http://localhost:8085/admin/id
    @GetMapping("/{id}")
    public Employees getEmployee(@PathVariable(value = "id") Long id){

        return manegementWorkerService.getByEmployee(id);
    }

    //    http://localhost:8085/admin/save
    @PostMapping("/save")
    public Employees saveEmployee(@RequestBody Employees employees){

        return manegementWorkerService.saveEmployee(employees);
    }

    // PUT - UPDATE
    // http://localhost:8085/admin/update
    @PutMapping ("/update/{id}")
    public Employees updateEmployee(@PathVariable(value="id") Long id,
                            @RequestBody Employees employees) {

        Employees employeeInfo= manegementWorkerService.getByEmployee(id);

        if( employeeInfo!= null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
            employeeInfo.setEmployeesId(id);
            employeeInfo.setName(employees.getName());
            employeeInfo.setLastName(employees.getLastName());
            employeeInfo.setPhoneNumber(employees.getPhoneNumber());
            employeeInfo.setInsideNumber(employees.getInsideNumber());
            return manegementWorkerService.updateEmployee(employeeInfo);
        }
        return null;
    }


    // DELETE - DELETE
    // http://localhost:8090/admin/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteEmployee(@PathVariable (value = "id") Long id) {
        return manegementWorkerService.deleteEmployee(id);
    }

}
