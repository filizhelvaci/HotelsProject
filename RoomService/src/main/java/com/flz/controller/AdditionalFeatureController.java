package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.AssetEntity;
import com.flz.service.AdditionalFeatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/additionalFeatures")
public class AdditionalFeatureController {

    // ****************** @AutoWired *************** //
    private final AdditionalFeatureService additionalFeatureService;

    public AdditionalFeatureController(AdditionalFeatureService additionalFeatureService) {
        this.additionalFeatureService = additionalFeatureService;
    }

    // ********************************************* //

    //    http://localhost:8082/additionalFeatures/getall
    @GetMapping("/getall")
    public List<AssetEntity> getAdditionalFeatures(){

        return additionalFeatureService.findAll();
    }


    //    http://localhost:8082/additionalFeatures/getone/
    @GetMapping("/getone/{id}")
    public Optional<AssetEntity> getAdditionalFeature(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return additionalFeatureService.findById(id);
    }

    //    http://localhost:8082/additionalFeatures/getsome/
    @GetMapping("/getsome")
    public ResponseEntity<List<AssetEntity>> getsomeAdditionalFeature(List<Long> ids){
        ResponseEntity<List<AssetEntity> > additionalFeatures=additionalFeatureService.getsomeAdditionalFeature(ids);
        return additionalFeatures;
    }


    //    http://localhost:8082/additionalFeatures/save
    @PostMapping("/save")
    public AssetEntity saveAdditionalFeature(@RequestBody AssetEntity additionalFeature){

        return additionalFeatureService.save(additionalFeature);
    }

    //    http://localhost:8082/additionalFeatures/save
    @PostMapping("/saveAll")
    public ResponseEntity<List<AssetEntity>> saveAllAdditionalFeature(@RequestBody List<AssetEntity> additionalFeatures){

        return ResponseEntity.ok().body((List<AssetEntity>) additionalFeatureService.saveAll(additionalFeatures));
    }

    // http://localhost:8082/additionalFeatures/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<AssetEntity> updateAdditionalFeature(@PathVariable(value="id") Long id,
                                                               @RequestBody AssetEntity additionalFeature) throws ResourceNotFoundException {

      return ResponseEntity.ok(additionalFeatureService.update(additionalFeature));
    }

    // http://localhost:8082/additionalFeatures/delete/
    @DeleteMapping ("/delete/{id}")
    public void deleteAdditionalFeature(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
       additionalFeatureService.deleteById(id);
    }

}
