package com.flz.controller;

import com.flz.model.User;
import com.flz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //    http://localhost:8087/user/all
    @GetMapping("/all")
    public List<User> getUsers(){

        return userService.getAllUsers();
    }

    //    http://localhost:8087/user/id
    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") Long id){

        return userService.getByUser(id);
    }

    //    http://localhost:8087/user/save
    @PostMapping("/save")
    public User saveUser(@RequestBody User user){

        return userService.saveUser(user);
    }


    // PUT - UPDATE
    // http://localhost:8087/user/update
    @PutMapping ("/update/{id}")
    public User updateUser(@PathVariable(value="id") Long id,
                            @RequestBody User user) {


        User userInfo= userService.getByUser(id);

        if(userInfo != null) {
            userInfo.setUserId(id);
            userInfo.setName(user.getName());
            userInfo.setLastName(user.getLastName());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhoneNumber(user.getPhoneNumber());
            return userService.updateUser(userInfo);
        }
        return null;
    }

    // DELETE - DELETE
    // http://localhost:8087/user/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteUser(@PathVariable (value = "id") Long id) {
        return userService.deleteUser(id);
    }
}
