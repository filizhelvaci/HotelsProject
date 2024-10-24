package com.flz.service;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.model.Customers;
import com.flz.repository.CustomersRepository;
import com.flz.utils.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class CustomersService extends ServiceManager<Customers,Long> {

    // ****************** @AutoWired *************** //
    private final CustomersRepository IcustomersRepository;

    public CustomersService(CustomersRepository IcustomersRepository) {
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