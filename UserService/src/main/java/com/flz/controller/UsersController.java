package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Users;
import com.flz.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel/users")
public class UsersController {

    // ****************** @AutoWired *************** //
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // *******************    ********************* //


    //    http://localhost:8084
    @GetMapping("/getUser")
    public String getUser() {

        return "tek user getirir";
    }

    //    http://localhost:8084/users/all
    @GetMapping("/all")
    public List<Users> getUsers(){

        return usersService.getAllUsers();
    }


    //    http://localhost:8084/users/id
    @GetMapping("/{id}")
    public Users getUser(@PathVariable(value = "id") Long id){

        return usersService.getByUser(id);
    }

    //    http://localhost:8084/users/save
    @PostMapping("/save")
    public Users saveUser(@RequestBody Users users){

        return usersService.saveUser(users);
    }

    // PUT - UPDATE
    // http://localhost:8084/users/update
    @PutMapping ("/update/{id}")
    public Users updateUser(@PathVariable(value="id") Long id,
                            @RequestBody Users users) throws ResourceNotFoundException {

        Users usersInfo= usersService.getByUser(id);

        if(usersInfo != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
            usersInfo.setUserId(id);
            usersInfo.setName(users.getName());
            usersInfo.setLastName(users.getLastName());
            usersInfo.setEmail(users.getEmail());
            usersInfo.setPhoneNumber(users.getPhoneNumber());
            return usersService.updateUsers(usersInfo);
        }
        return null;

        /*
        users.setUserId(id);
        return usersService.updateUsers(users)

        return studentService.updateOneStudent2(id, student);
         */
    }


    // DELETE - DELETE
    // http://localhost:8084/user/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteUser(@PathVariable (value = "id") Long id) {
        return usersService.deleteUser(id);
    }
}
