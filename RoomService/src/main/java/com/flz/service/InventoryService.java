package com.flz.service;

import com.flz.model.RoomTypeAssetEntity;
import com.flz.repository.IInventoryRepository;
import com.flz.utils.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends ServiceManager<RoomTypeAssetEntity,Long> {

    // ****************** @AutoWired *************** //
    private final IInventoryRepository IinventoryRepository;

    public InventoryService(IInventoryRepository IinventoryRepository) {
        super(IinventoryRepository);
        this.IinventoryRepository = IinventoryRepository;
    }

}
