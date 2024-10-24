package com.flz.service;

import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.model.Employees;
import com.flz.repository.EmployeesRepository;
import com.flz.utils.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class EmployeesService extends ServiceManager<Employees,Long> {

    private final EmployeesRepository IemployeesRepository;

    public EmployeesService(EmployeesRepository IemployeesRepository) {
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
