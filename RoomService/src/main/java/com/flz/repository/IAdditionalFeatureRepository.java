package com.flz.repository;

import com.flz.model.AdditionalFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdditionalFeatureRepository extends JpaRepository<AdditionalFeature,Long> {
    List<AdditionalFeature> findByIdIn(List<Long> ids);

    Optional<AdditionalFeature> findAdditionalFeatureByFeatureName (String Name);

    //Optional<AdditionalFeature>

}
