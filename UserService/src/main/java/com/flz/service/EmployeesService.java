package com.flz.service;

import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Employees;
import com.flz.model.Users;
import com.flz.repository.IEmployeesRepository;
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
public class EmployeesService extends ServiceManager<Employees,Long> {

    private final IEmployeesRepository IemployeesRepository;

    public EmployeesService(IEmployeesRepository IemployeesRepository) {
        super(IemployeesRepository);
        this.IemployeesRepository = IemployeesRepository;
    }

    @Transactional
    public Employees saveEmployee(DoEmployeeRegisterRequestDto userDTO) {
         Employees employees = new Employees();
        employees.setIDnumber(userDTO.getIDnumber());
        employees.setGraduationStatus(userDTO.getGraduationStatus());
        employees.setInsideNumber(userDTO.getInsideNumber());
        employees.setBirthDate(userDTO.getBirthDate());
        employees.setContractPeriod(userDTO.getContractPeriod());
        employees.setGraduationYear(userDTO.getGraduationYear());

        return IemployeesRepository.save(employees);
    }


}
