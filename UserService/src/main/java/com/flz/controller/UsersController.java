package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Users;
import com.flz.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    // ****************** @AutoWired *************** //
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // *******************    ********************* //


    //    http://localhost:8081/users/all
    @GetMapping("/getall")
    public List<Users> getUsers(){

        return usersService.getAllUsers();
    }


    //    http://localhost:8081/users/getone/
    @GetMapping("/getone/{id}")
    public ResponseEntity<Users> getUser(@PathVariable(value = "id") Long id)throws ResourceNotFoundException{

        return usersService.getByUser(id);
    }

    //    http://localhost:8084/users/save
    @PostMapping("/save")
    public Users saveUser(@RequestBody Users users){

        return usersService.saveUser(users);
    }

    // http://localhost:8081/users/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value="id") Long id,
                            @RequestBody Users users) throws ResourceNotFoundException {

      return usersService.updateUsers(id, users);
    }

    // http://localhost:8081/users/delete/
    @DeleteMapping ("/delete/{id}")
    public Map<String,Boolean> deleteUser(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {

        return usersService.deleteUser(id);
    }
}
