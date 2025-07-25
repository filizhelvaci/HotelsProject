package com.flz.repository;

import com.flz.model.entity.EmployeeOldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeOldRepository extends JpaRepository<EmployeeOldEntity, Long> {

}
