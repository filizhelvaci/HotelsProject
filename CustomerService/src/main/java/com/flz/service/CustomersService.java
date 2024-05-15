package com.flz.service;

import com.flz.model.Customer;
import com.flz.repository.ICustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CustomersService {

    @Autowired
    ICustomersRepository IcustomersRepository;


    public List<Customer> getAllCustomers() {
        return IcustomersRepository.findAll();
    }

    public Customer getByCustomer(Long id) {
        return IcustomersRepository.findById(id).get();
    }

    public Customer saveCustomer(Customer customer){
        return IcustomersRepository.save(customer);
    }

    public String deleteCutomer(Long id){
        // silme işleminde bi onay daha istenebilir x kişisini silmek istediğinizden emin misiniz? gibi
        IcustomersRepository.deleteById(id);
        return "Customer = "+id+ "deleted ";
    }

    public Customer updateCustomer(Customer customer){

        return IcustomersRepository.save(customer);
    }

}
