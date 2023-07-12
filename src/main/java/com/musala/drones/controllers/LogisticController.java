package com.musala.drones.controllers;

import com.musala.drones.domain.DroneMedications;
import com.musala.drones.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LogisticController {

    @Autowired
    LogisticService logisticService;

    @PostMapping("/drone/{droneId}/load/medications")
    public ResponseEntity<DroneMedications> loadMedicationsOnDrone(@PathVariable String droneId, @RequestBody List<String> medicationIds) {
        DroneMedications droneMedications = logisticService.loadMedicationsOnDrone(droneId, medicationIds);
        return ResponseEntity.ok(droneMedications);
    }
}
