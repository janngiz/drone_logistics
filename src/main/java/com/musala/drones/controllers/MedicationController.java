package com.musala.drones.controllers;

import com.musala.drones.entities.Medication;
import com.musala.drones.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotNull;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/medication")
public class MedicationController {


    @Autowired
    private MedicationService medicationService;

    @PostMapping("/save")
    public ResponseEntity<Medication> saveMedication(@RequestParam(value = "file", required = false) MultipartFile file,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("weight") @NotNull String weight,
                                                     @RequestParam("code") String code) {
        Medication savedMedication = medicationService.saveMedication(name, weight, code, file);
        return ResponseEntity.ok(savedMedication);
    }


}
