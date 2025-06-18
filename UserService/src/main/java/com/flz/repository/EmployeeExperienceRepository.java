package com.flz.repository;

import com.flz.model.entity.EmployeeExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmployeeExperienceRepository extends JpaRepository<EmployeeExperienceEntity, Integer> {

    boolean existsByEmployeeIdAndPositionIdAndStartDate(Long employeeId, Long positionId, LocalDate startDate);

    Optional<EmployeeExperienceEntity> findTopByEmployeeIdOrderByStartDateDesc(Long employeeId);

}
