package com.flz.service;

import com.flz.model.AssetEntity;
import com.flz.repository.IAdditionalFeatureRepository;
import com.flz.utils.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalFeatureService extends ServiceManager<AssetEntity,Long> {

    // ****************** @AutoWired *************** //
    private final IAdditionalFeatureRepository IadditionalFeatureRepository;

    public AdditionalFeatureService(IAdditionalFeatureRepository IadditionalFeatureRepository) {
        super(IadditionalFeatureRepository);
        this.IadditionalFeatureRepository = IadditionalFeatureRepository;
    }

    public ResponseEntity<List<AssetEntity>> getsomeAdditionalFeature(List<Long> ids) {
         List<AssetEntity> additionalFeature=IadditionalFeatureRepository.findByIdIn(ids);
        return ResponseEntity.ok().body(additionalFeature);
    }
}
