package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Employees;
import com.flz.repository.IEmployeesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class EmployeesService {

    @Autowired
    IEmployeesRepository IemployeesRepository;

    public List<Employees> getAllEmployees() {
        return IemployeesRepository.findAll();
    }

    public ResponseEntity<Employees> getByEmployee(Long id)throws ResourceNotFoundException {

        Employees employees=IemployeesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employeed not found ID : "+id));
        return ResponseEntity.ok().body(employees);
    }

    public Employees saveEmployee(Employees employees){

        if(IemployeesRepository.findById(employees.getId()).isPresent())
            return null;
        return IemployeesRepository.save(employees);
    }

    public Map<String,Boolean> deleteEmployee(Long id) throws ResourceNotFoundException{

        Employees employees=IemployeesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found ID : "+id));

        IemployeesRepository.deleteById(id);
        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted "+id, Boolean.TRUE);

        return response;
    }

    public ResponseEntity<Employees> updateEmployee(Long id,Employees employees) throws ResourceNotFoundException{

        IemployeesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException( "Employee not found ID : "+id));
        employees.setId(id);
        return ResponseEntity.ok(IemployeesRepository.save(employees));
    }

}
