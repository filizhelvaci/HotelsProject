package com.flz.service.impl;

import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.exception.EmployeeAlreadyManagerException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.service.DepartmentWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class DepartmentWriteServiceImpl implements DepartmentWriteService {

    private final DepartmentSavePort departmentSavePort;
    private final DepartmentReadPort departmentReadPort;
    private final EmployeeReadPort employeeReadPort;

    private static final DepartmentCreateRequestToDomainMapper
            departmentCreateRequestToDomainMapper = DepartmentCreateRequestToDomainMapper.INSTANCE;


    @Override
    public void create(DepartmentCreateRequest createRequest) {

        boolean existsByName = departmentReadPort.existsByName(createRequest.getName());
        if (existsByName) {
            throw new DepartmentAlreadyExistsException(createRequest.getName());
        }

        Long managerId = createRequest.getManagerId();

        Employee manager = employeeReadPort.findById(managerId)
                .orElseThrow(() -> new EmployeeNotFoundException(managerId));

        Optional<Department> departmentOfManager= departmentReadPort
                .findByManagerIdAndStatus(managerId, DepartmentStatus.ACTIVE);
        if (departmentOfManager.isPresent()) {

            throw new EmployeeAlreadyManagerException(departmentOfManager.get().getName());
        }

        Department department = departmentCreateRequestToDomainMapper.map(createRequest);
        department.setManager(manager);
        departmentSavePort.save(department);
    }


    @Override
    public void update(Long id, DepartmentUpdateRequest departmentUpdateRequest) {

        Department department = departmentReadPort.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        Long managerId = departmentUpdateRequest.getManagerId();

        Employee manager = employeeReadPort.findById(managerId)
                .orElseThrow(() -> new EmployeeNotFoundException(managerId));

        if (!(department.getManager().getId().equals(managerId))) {
            checkIfManagerExists(departmentUpdateRequest);
        }

        String name = departmentUpdateRequest.getName();
        if (!(department.getName().equals(name))) {
            checkIfDepartmentNameExists(departmentUpdateRequest);
        }

        department.update(departmentUpdateRequest.getName(), manager);
        departmentSavePort.save(department);
    }

    private void checkIfDepartmentNameExists(DepartmentUpdateRequest departmentUpdateRequest) {

        boolean existsByName = departmentReadPort
                .existsByName(departmentUpdateRequest.getName());
        if (existsByName) {
            throw new DepartmentAlreadyExistsException(departmentUpdateRequest.getName());
        }
    }

    private void checkIfManagerExists(DepartmentUpdateRequest departmentUpdateRequest) {

        Optional<Department> departmentOfManager= departmentReadPort
                .findByManagerIdAndStatus(departmentUpdateRequest.getManagerId(), DepartmentStatus.ACTIVE);
        if (departmentOfManager.isPresent()) {
            throw new EmployeeAlreadyManagerException(departmentOfManager.get().getName());
        }
    }


    @Override
    public void delete(Long id) {

        Department department = departmentReadPort.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        department.delete(id);
        departmentSavePort.save(department);
    }

}
