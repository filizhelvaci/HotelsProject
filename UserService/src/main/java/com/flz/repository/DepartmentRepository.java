package com.flz.repository;

import com.flz.model.entity.DepartmentEntity;
import com.flz.model.enums.DepartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    boolean existsByName(String name);

    boolean existsByManagerId(Long id);

    boolean existsByManagerIdAndStatus(Long managerId, DepartmentStatus status);

    Optional<DepartmentEntity> findByManagerIdAndStatus(Long managerId, DepartmentStatus status);

    Optional<DepartmentEntity> findByName(String name);

    Optional<DepartmentEntity> findByManagerId(Long id);

}
