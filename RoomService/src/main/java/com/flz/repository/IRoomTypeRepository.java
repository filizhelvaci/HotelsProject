package com.flz.repository;

import com.flz.model.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomTypeRepository extends JpaRepository<RoomTypeEntity,Long> {
}
