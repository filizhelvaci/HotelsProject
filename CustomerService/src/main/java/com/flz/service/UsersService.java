package com.flz.service;

import com.flz.model.Users;
import com.flz.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    IUsersRepository iUsersRepository;

    public List<Users> getAllUsers() {
        return iUsersRepository.findAll();
    }

    public Users getByUser(Long id) {
        return iUsersRepository.findById(id).get();
    }

    public Users saveUser(Users users){
        return iUsersRepository.save(users);
    }

    public String deleteUser(Long id){
        iUsersRepository.deleteById(id);
        return "user = "+id+ "deleted ";
    }

    public Users updateUsers(Users users){
        return iUsersRepository.save(users);
    }


}
