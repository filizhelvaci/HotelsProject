package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Customers;
import com.flz.repository.ICustomersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CustomersService {

    @Autowired
    ICustomersRepository IcustomersRepository;


    public List<Customers> getAllCustomers() {
        return IcustomersRepository.findAll();
    }

    public ResponseEntity<Customers> getByCustomer(Long id)throws ResourceNotFoundException {

        Customers customers=IcustomersRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found ID : "+ id));

        return ResponseEntity.ok().body(customers);
    }


    public Customers saveCustomer(Customers customer){
        return IcustomersRepository.save(customer);
    }

    public Map<String,Boolean> deleteCustomer(Long id) throws ResourceNotFoundException{

        Customers customers=IcustomersRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found ID : " +id));

        IcustomersRepository.deleteById(id);

        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted "+id ,Boolean.TRUE);

        return response;
    }



    public ResponseEntity<Customers> updateCustomer(Long id, Customers customers) throws ResourceNotFoundException {

        IcustomersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "Customer not found ID : "+id));
        customers.setId(id);
        return ResponseEntity.ok(IcustomersRepository.save(customers));
    }


}
