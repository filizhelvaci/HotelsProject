package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Inventory;
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
    public List<Inventory> getInventory(){

        return inventoryService.findAll();
    }


    //    http://localhost:8082/Inventory/getone/
    @GetMapping("/getone/{id}")
    public Optional<Inventory> getAdditionalFeature(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return inventoryService.findById(id);
    }


    //    http://localhost:8082/Inventory/save
    @PostMapping("/save")
    public Inventory saveAdditionalFeature(@RequestBody Inventory additionalFeature){

        return inventoryService.save(additionalFeature);
    }

    //    http://localhost:8082/Inventory/save
    @PostMapping("/saveAll")
    public ResponseEntity<List<Inventory>> saveAllAdditionalFeature(@RequestBody List<Inventory> additionalFeatures){

        return ResponseEntity.ok().body((List<Inventory>) inventoryService.saveAll(additionalFeatures));
    }

    // http://localhost:8082/Inventory/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<Inventory> updateAdditionalFeature(@PathVariable(value="id") Long id,
                                                        @RequestBody Inventory additionalFeature) throws ResourceNotFoundException {

      return ResponseEntity.ok(inventoryService.update(additionalFeature));
    }

    // http://localhost:8082/Inventory/delete/
    @DeleteMapping ("/delete/{id}")
    public void deleteAdditionalFeature(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
        inventoryService.deleteById(id);
    }

}
