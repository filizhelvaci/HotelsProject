package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.RoomTypeAssetEntity;
import com.flz.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Inventory")
public class InventoryController {

    // ****************** @AutoWired *************** //
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // ********************************************* //

    //    http://localhost:8082/Inventory/getall
    @GetMapping("/getall")
    public List<RoomTypeAssetEntity> getInventory(){

        return inventoryService.findAll();
    }


    //    http://localhost:8082/Inventory/getone/
    @GetMapping("/getone/{id}")
    public Optional<RoomTypeAssetEntity> getAdditionalFeature(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return inventoryService.findById(id);
    }


    //    http://localhost:8082/Inventory/save
    @PostMapping("/save")
    public RoomTypeAssetEntity saveAdditionalFeature(@RequestBody RoomTypeAssetEntity additionalFeature){

        return inventoryService.save(additionalFeature);
    }

    //    http://localhost:8082/Inventory/save
    @PostMapping("/saveAll")
    public ResponseEntity<List<RoomTypeAssetEntity>> saveAllAdditionalFeature(@RequestBody List<RoomTypeAssetEntity> additionalFeatures){

        return ResponseEntity.ok().body((List<RoomTypeAssetEntity>) inventoryService.saveAll(additionalFeatures));
    }

    // http://localhost:8082/Inventory/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<RoomTypeAssetEntity> updateAdditionalFeature(@PathVariable(value="id") Long id,
                                                                       @RequestBody RoomTypeAssetEntity additionalFeature) throws ResourceNotFoundException {

      return ResponseEntity.ok(inventoryService.update(additionalFeature));
    }

    // http://localhost:8082/Inventory/delete/
    @DeleteMapping ("/delete/{id}")
    public void deleteAdditionalFeature(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
        inventoryService.deleteById(id);
    }

}
