package com.flz.repository;

import com.flz.model.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {

    boolean existsByName(String name);
}
