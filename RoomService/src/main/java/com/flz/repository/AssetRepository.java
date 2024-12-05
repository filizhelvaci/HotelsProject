package com.flz.repository;

import com.flz.model.entity.AssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

    Boolean existsByName(String name);

    Page<AssetEntity> findByNameContaining(String name, Pageable pageable);

    Page<AssetEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<AssetEntity> findByIsDefault(Boolean isDefault, Pageable pageable);
}
