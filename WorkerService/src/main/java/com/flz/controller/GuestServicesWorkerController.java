package com.flz.controller;

import com.flz.model.Users;
import com.flz.service.GuestServicesWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class GuestServicesWorkerController {

    @Autowired
    GuestServicesWorkerService guestServicesWorkerService;

    //    http://localhost:8085/users/all
    @GetMapping("/all")
    public List<Users> getUsers(){

        return guestServicesWorkerService.getAllUsers();
     }


    //    http://localhost:8085/users/id
     @GetMapping("/{id}")
    public Users getUser(@PathVariable (value = "id") Long id){

        return guestServicesWorkerService.getByUser(id);
     }

    //    http://localhost:8085/users/save
    @PostMapping("/save")
    public Users saveUser(@RequestBody Users users){

        return guestServicesWorkerService.saveUser(users);
    }

    // PUT - UPDATE
    // http://localhost:8085/users/update
    @PutMapping ("/update/{id}")
    public Users updateUser(@PathVariable(value="id") Long id,
                                 @RequestBody Users users) {
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

        Users usersInfo= guestServicesWorkerService.getByUser(id);

        if(usersInfo != null) {
            //yanlış veri gönderildiyse yada en az bir kontrolden sonra kayıt değiştirlmeli
            //değiştirilmek istenen kayıt gösterilerek uyarı vermeli. bir onay daha alırsa değişiklik yapılmalı
            // yada update işlemi başka bir yetki ile belirlenmeli.
           usersInfo.setUserId(id);
           usersInfo.setName(users.getName());
           usersInfo.setLastName(users.getLastName());
           usersInfo.setEmail(users.getEmail());
           usersInfo.setCity(users.getCity());
           usersInfo.setNationality(users.getNationality());
           usersInfo.setBirthDate(users.getBirthDate());
           usersInfo.setIDnumber(users.getIDnumber());
           usersInfo.setPhoneNumber(users.getPhoneNumber());
           return guestServicesWorkerService.updateUsers(usersInfo);
        }
        return null;
    }


    // DELETE - DELETE
    // http://localhost:8090/user/delete/
    @DeleteMapping ("/delete/{id}")
    public String deleteUser(@PathVariable (value = "id") Long id) {
        return guestServicesWorkerService.deleteUser(id);
    }

}
