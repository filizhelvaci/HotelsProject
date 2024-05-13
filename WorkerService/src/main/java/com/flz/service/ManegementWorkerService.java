package com.flz.service;

import com.flz.model.Employees;
import com.flz.model.Users;
import com.flz.repository.ManegementWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManegementWorkerService {

    @Autowired
    ManegementWorkerRepository manegementWorkerRepository;

    public List<Employees> getAllEmployees() {
        return manegementWorkerRepository.findAll();
    }

    public Employees getByEmployee(Long id) {
        return manegementWorkerRepository.findById(id).get();
    }

    public Employees saveEmployee(Employees employees){
        return manegementWorkerRepository.save(employees);
    }

    public String deleteEmployee(Long id){
        manegementWorkerRepository.deleteById(id);
        return "employee = "+id+ " deleted ";
    }

    public Employees updateEmployee(Employees employees){
        return manegementWorkerRepository.save(employees);
    }

}
