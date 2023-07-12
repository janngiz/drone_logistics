package com.musala.drones.controllers;

import com.musala.drones.domain.MedicationResponse;
import com.musala.drones.entities.Medication;
import com.musala.drones.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/medication")
public class MedicationController {


    @Autowired
    private MedicationService medicationService;

    @PostMapping(value ="/save",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Medication> saveMedication(@RequestParam(value = "file", required = false) MultipartFile file,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("weight") String weight,
                                                     @RequestParam("code") String code) {
        Medication savedMedication = medicationService.saveMedication(name, weight, code, file);
        return ResponseEntity.ok(savedMedication);
    }

    @GetMapping(value = "/{medicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationResponse> getMedication(@PathVariable String medicationId) {
        Medication medication = medicationService.getMedicationById(medicationId);

        if (medication != null) {
            MedicationResponse medicationResponse = new MedicationResponse(medication.getId(), medication.getName(), medication.getWeight(),
                    medication.getCode(), getImageUrl(medication.getImage()));
            return ResponseEntity.ok(medicationResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Medication>> getAllMedications() {
        List<Medication> medications = medicationService.getAllMedications();
        return ResponseEntity.ok(medications);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Medication> updateMedication(@PathVariable("id") String id,
                                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                                       @RequestParam("name") String name,
                                                       @RequestParam("weight") String weight,
                                                       @RequestParam("code") String code) {
        Medication updatedMedication = medicationService.updateMedication(id, name, weight, code, file);
        return ResponseEntity.ok(updatedMedication);
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get("uploads", fileName);
            Resource imageResource = new UrlResource(imagePath.toUri());

            if (imageResource.exists() && imageResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageResource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }


    private String getImageUrl(String imagePath) {
        if (imagePath != null && !imagePath.isBlank()) {
            return "/medication/image/" + imagePath;
        }
        return null;
    }


}
