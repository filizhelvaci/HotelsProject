package com.flz.service;

import com.flz.model.Employees;
import com.flz.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {

    @Autowired
    EmployeesRepository employeesRepository;

    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }

    public Employees getByEmployee(Long id) {
        return employeesRepository.findById(id).get();
    }

    public Employees saveEmployee(Employees employees){
        return employeesRepository.save(employees);
    }

    public String deleteEmployee(Long id){
        employeesRepository.deleteById(id);
        return "employee = "+id+ "deleted ";
    }

    public Employees updateEmployee(Employees employees){
        return employeesRepository.save(employees);
    }

}
