package com.flz.repository;

import com.flz.model.entity.AssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

    boolean existsByName(String name);

    Page<AssetEntity> findByNameContainingAndPriceBetweenAndIsDefault(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean isDefault,
            Pageable pageable
    );
}
