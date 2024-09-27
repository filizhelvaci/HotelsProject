package com.flz.repository;

import com.flz.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomsRepository extends JpaRepository<RoomEntity,Long>{
}
