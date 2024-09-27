package com.flz.repository;

import com.flz.model.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdditionalFeatureRepository extends JpaRepository<AssetEntity,Long> {
    List<AssetEntity> findByIdIn(List<Long> ids);

    Optional<AssetEntity> findAdditionalFeatureByFeatureName (String Name);

    //Optional<AdditionalFeature>

}
