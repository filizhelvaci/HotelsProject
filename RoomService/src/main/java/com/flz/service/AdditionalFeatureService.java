package com.flz.service;

import com.flz.model.AdditionalFeature;
import com.flz.model.BaseEntity;
import com.flz.repository.IAdditionalFeatureRepository;
import com.flz.utils.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdditionalFeatureService extends ServiceManager<AdditionalFeature,Long> {

    // ****************** @AutoWired *************** //
    private final IAdditionalFeatureRepository IadditionalFeatureRepository;

    public AdditionalFeatureService(IAdditionalFeatureRepository IadditionalFeatureRepository) {
        super(IadditionalFeatureRepository);
        this.IadditionalFeatureRepository = IadditionalFeatureRepository;
    }

    public ResponseEntity<List<AdditionalFeature>> getsomeAdditionalFeature(List<Long> ids) {
         List<AdditionalFeature> additionalFeature=IadditionalFeatureRepository.findByIdIn(ids);
        return ResponseEntity.ok().body(additionalFeature);
    }
}
