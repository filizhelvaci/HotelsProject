package com.flz.repository;

import com.flz.model.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssetRepository extends JpaRepository<AssetEntity,Long> {

}