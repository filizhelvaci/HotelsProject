package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Customers;
import com.flz.model.Users;
import com.flz.repository.IUsersRepository;
import com.flz.utils.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UsersService extends ServiceManager<Users,Long> {

    private final IUsersRepository IusersRepository;

    public UsersService(IUsersRepository IusersRepository) {
        super(IusersRepository);
        this.IusersRepository = IusersRepository;
    }


    //  CustomersService customersService;
//
//    public List<Users> getAllUsers() {
//        return IusersRepository.findAll();
//    }
//
//    public ResponseEntity<Users> getByUser(Long id)throws ResourceNotFoundException {
//
//        Users users=IusersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found ID: "+ id));
//
//        return ResponseEntity.ok().body(users);
//    }
//
//
//    public Users saveUser(Users users){
//
//        if(IusersRepository.findById(users.getId()).isPresent())
//            return null;
//
//        return IusersRepository.save(users);
//    }
//
//    public Map<String,Boolean> deleteUser(Long id) throws ResourceNotFoundException{
//
//        Users users=IusersRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("Customer not found ID : " +id));
//
//        IusersRepository.deleteById(id);
//
//        Map<String,Boolean> response=new HashMap<>();
//        response.put("Deleted "+id ,Boolean.TRUE);
//
//        return response;
//    }
//
//    public ResponseEntity<Users> updateUsers(Long id,Users users)throws ResourceNotFoundException{
//
//        IusersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "User not found ID : "+id));
//        users.setId(id);
//        return ResponseEntity.ok(IusersRepository.save(users));
//
//    }

}
