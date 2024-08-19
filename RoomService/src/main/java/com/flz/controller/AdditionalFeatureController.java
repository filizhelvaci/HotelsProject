package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.AdditionalFeature;
import com.flz.service.AdditionalFeatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    public List<AdditionalFeature> getAdditionalFeatures(){

        return additionalFeatureService.findAll();
    }


    //    http://localhost:8082/additionalFeatures/getone/
    @GetMapping("/getone/{id}")
    public Optional<AdditionalFeature> getAdditionalFeature(@PathVariable(value = "id") Long id)throws ResourceNotFoundException {

        return additionalFeatureService.findById(id);
    }

    //    http://localhost:8082/additionalFeatures/getsome/
    @GetMapping("/getsome")
    public ResponseEntity<List<AdditionalFeature>> getsomeAdditionalFeature(List<Long> ids){
        ResponseEntity<List<AdditionalFeature> > additionalFeatures=additionalFeatureService.getsomeAdditionalFeature(ids);
        return additionalFeatures;
    }

    // fixme Save yaparken den fazla nesne oluşturabilmeliyiz. Örneğin yatak alındıysa hepsini tek tek oluşturmak mantıksız

    //    http://localhost:8082/additionalFeatures/save
    @PostMapping("/save")
    public AdditionalFeature saveAdditionalFeature(@RequestBody AdditionalFeature additionalFeature){

        return additionalFeatureService.save(additionalFeature);
    }

    //    http://localhost:8082/additionalFeatures/save
    @PostMapping("/saveAll")
    public ResponseEntity<List<AdditionalFeature>> saveAllAdditionalFeature(@RequestBody List<AdditionalFeature> additionalFeatures){

        return ResponseEntity.ok().body((List<AdditionalFeature>) additionalFeatureService.saveAll(additionalFeatures));
    }

    // http://localhost:8082/additionalFeatures/update/
    @PutMapping ("/update/{id}")
    public ResponseEntity<AdditionalFeature> updateAdditionalFeature(@PathVariable(value="id") Long id,
                                                        @RequestBody AdditionalFeature additionalFeature) throws ResourceNotFoundException {

      return ResponseEntity.ok(additionalFeatureService.update(additionalFeature));
    }

    // http://localhost:8082/additionalFeatures/delete/
    @DeleteMapping ("/delete/{id}")
    public void deleteAdditionalFeature(@PathVariable (value = "id") Long id)throws ResourceNotFoundException {
       additionalFeatureService.deleteById(id);
    }

}
