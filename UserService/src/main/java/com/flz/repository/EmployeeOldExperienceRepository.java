package com.flz.repository;

import com.flz.model.entity.EmployeeOldExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeOldExperienceRepository extends JpaRepository<EmployeeOldExperienceEntity, Long> {

    List<EmployeeOldExperienceEntity> findAllByEmployeeOld_Id(Long employeeOldId);

}
