package com.flz.service;

import com.flz.model.AdditionalFeature;
import com.flz.model.Inventory;
import com.flz.repository.IAdditionalFeatureRepository;
import com.flz.repository.IInventoryRepository;
import com.flz.utils.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends ServiceManager<Inventory,Long> {

    // ****************** @AutoWired *************** //
    private final IInventoryRepository IinventoryRepository;

    public InventoryService(IInventoryRepository IinventoryRepository) {
        super(IinventoryRepository);
        this.IinventoryRepository = IinventoryRepository;
    }

}
