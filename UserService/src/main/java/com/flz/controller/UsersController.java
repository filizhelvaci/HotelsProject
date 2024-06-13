package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Users;
import com.flz.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    // ****************** @AutoWired *************** //
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // ********************************************* //


    //    http://localhost:8083/users/all
    @GetMapping("/getall")
    public List<Users> getUsers(){

        return usersService.findAll();
    }


    //    http://localhost:8083/users/getone/
    @GetMapping("/getone/{id}")
    public Optional<Users> getUser(@PathVariable(value = "id") Long id)throws ResourceNotFoundException{

        return usersService.findById(id);
    }

    //    http://localhost:8083/users/save
    @PostMapping("/save")
    public Users saveUser(@RequestBody Users users){

        return usersService.save(users);
    }


    //FIXME update id almadan nasıl güncelleme yapacak?

    // http://localhost:8083/users/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value="id") Long id,
                            @RequestBody Users users) throws ResourceNotFoundException {

      return ResponseEntity.ok(usersService.update( users));
    }

    // http://localhost:8083/users/delete/
    @DeleteMapping ("/delete/{id}")
    public void deleteUser(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
       usersService.deleteById(id);
    }
}
