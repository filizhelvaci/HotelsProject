package com.flz.repository;

import com.flz.model.entity.RoomTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {

    boolean existsByName(String name);

    Page<RoomTypeEntity> findByNameContainingAndPriceBetween(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );
}
