package com.flz.repository;

import com.flz.model.entity.EmployeeExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeExperienceRepository extends JpaRepository<EmployeeExperienceEntity, Integer>, JpaSpecificationExecutor<EmployeeExperienceEntity> {

    boolean existsByEmployeeIdAndPositionIdAndStartDate(Long employeeId, Long positionId, LocalDate startDate);

    Optional<EmployeeExperienceEntity> findTopByEmployeeIdOrderByStartDateDesc(Long employeeId);

    List<EmployeeExperienceEntity> findAllByEmployee_Id(Long employeeId);

    void deleteAllByEmployee_Id(Long employeeId);

}
