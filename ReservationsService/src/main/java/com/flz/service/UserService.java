package com.flz.service;

import com.flz.model.User;
import com.flz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

        @Autowired
        UserRepository userRepository;

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        public User getByUser(Long id) {
            return userRepository.findById(id).get();
        }

        public User saveUser(User user){
            return userRepository.save(user);
        }

        public String deleteUser(Long id){
            userRepository.deleteById(id);
            return "user = "+id+ "deleted ";
        }

        public User updateUser(User user){
            return userRepository.save(user);
        }

}


