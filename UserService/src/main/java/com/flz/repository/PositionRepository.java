package com.flz.repository;

import com.flz.model.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    boolean existsByName(String name);

    Optional<PositionEntity> findByName(String name);

}
