package com.flz.repository;

import com.flz.model.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long>, JpaSpecificationExecutor<RoomTypeEntity> {

    boolean existsByName(String name);
}
