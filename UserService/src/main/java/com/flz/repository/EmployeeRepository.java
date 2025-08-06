package com.flz.repository;

import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    boolean existsByIdentityNumber(String identityNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    EmployeeEntity findByIdentityNumber(String identityNumber);

    @Query("SELECT new com.flz.model.Employee(e.id, e.firstName, e.lastName) FROM EmployeeEntity e")
    List<Employee> findEmployeeSummaries();

}
