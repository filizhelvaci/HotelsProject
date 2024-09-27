package com.flz.repository;

import com.flz.model.RoomTypeAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryRepository extends JpaRepository<RoomTypeAssetEntity,Long> {

}
