package com.flz.controller;

import com.flz.model.Employees;
import com.flz.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    //    http://localhost:8085/employees/id
     @GetMapping("/{id}")
    public Employees getEmployee(@PathVariable (value = "id") Long id){

        return employeesService.getByEmployee(id);
     }

    //    http://localhost:8085/employees/save
    @PostMapping("/save")
    public Employees saveEmployee(@RequestBody Employees employee){

        return employeesService.saveEmployee(employee);
    }

    // PUT - UPDATE
    // http://localhost:8085/employee/update
    @PutMapping ("/update/{id}")
    public Employees updateEmployee(@PathVariable(value="id") Long id,
                                 @RequestBody Employees employees) {
        //id 0 dan büyük olmalı ve userId nin olusturduğu randevunun çıkış tarihi geçmemiş olmalı
        // Geçmişe yönelik bir veri kaybını engeller

        //Optional<Long> uId=guestServicesWorkerService.getByUser(id).getId();

        // Kullanıcı adı null olan kullanıcının adını al
        //Optional<String> name2 = user2.getName();
       /*
        if (uId.isPresent()) {
            System.out.println("User id: " + uId.get());
        } else {
            System.out.println("User id doesn't use.");
        }


        // Kullanıcı adı null olmayan kullanıcının adını al ve default bir değer kullan
        String defaultName = user1.getName().orElse("Bilinmeyen");
        System.out.println("Kullanıcı adı: " + defaultName);

        // Kullanıcı adı null olan kullanıcının adını al ve default bir değer kullan
        String defaultName2 = user2.getName().orElse("Bilinmeyen");
        System.out.println("Kullanıcı adı: " + defaultName2);*/

        Employees employees1= employeesService.getByEmployee(id);

        if(employees1 != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
           employees1.setEmployeesId(id);
           employees1.setName(employees.getName());
           employees1.setLastName(employees.getLastName());
           employees1.setEmail(employees.getEmail());
           employees1.setInsideNumber(employees.getInsideNumber());
           employees1.setPhoneNumber(employees.getPhoneNumber());
           employees1.setNationality(employees.getNationality());
           employees1.setAddress(employees.getAddress());
           employees1.setBirthDate(employees.getBirthDate());
           employees1.setStartDate(employees.getStartDate());
           employees1.setExitDate(employees.getExitDate());
           employees1.setIDnumber(employees.getIDnumber());
           employees1.setSalary(employees.getSalary());
           return employeesService.updateEmployee(employees1);
        }
        return null;
    }


    // DELETE - DELETE
    // http://localhost:8090/user/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteEmployee(@PathVariable (value = "id") Long id) {
        return employeesService.deleteEmployee(id);
    }

}
