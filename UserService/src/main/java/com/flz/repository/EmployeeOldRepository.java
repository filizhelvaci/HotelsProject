package com.flz.repository;

import com.flz.model.entity.EmployeeOldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeOldRepository extends JpaRepository<EmployeeOldEntity, Long> {

    Optional<EmployeeOldEntity> findByIdentityNumber(String identityNumber);

}
