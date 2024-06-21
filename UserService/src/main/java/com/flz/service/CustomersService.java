package com.flz.service;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Customers;
import com.flz.model.Employees;
import com.flz.repository.ICustomersRepository;
import com.flz.repository.IUsersRepository;
import com.flz.utils.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomersService extends ServiceManager<Customers,Long> {

    // ****************** @AutoWired *************** //
    private final ICustomersRepository IcustomersRepository;

    public CustomersService(ICustomersRepository IcustomersRepository) {
        super(IcustomersRepository);
        this.IcustomersRepository = IcustomersRepository;
    }
    // *********************************************** //

    @Transactional
    public Customers saveCustomer(DoCustomerRegisterRequestDto userDTO) {
        Customers customers = new Customers();
        customers.setIDnumber(userDTO.getIDnumber());
        customers.setBirthDate(userDTO.getBirthDate());
        customers.setNationality(userDTO.getNationality());

        return IcustomersRepository.save(customers);
    }
}