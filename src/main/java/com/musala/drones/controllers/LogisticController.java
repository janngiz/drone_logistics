package com.musala.drones.controllers;

import com.musala.drones.domain.DroneMedications;
import com.musala.drones.entities.Drone;
import com.musala.drones.entities.Medication;
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

    @PostMapping("/deliver/medications/drone/{droneId}")
    public ResponseEntity<DroneMedications> deliverMedication(@PathVariable String droneId) {
        DroneMedications droneMedications = logisticService.deliverMedication(droneId);
        return ResponseEntity.ok(droneMedications);
    }

    @GetMapping("/medications/drone/{droneId}")
    public ResponseEntity<List<Medication>> getLoadedMedicationByDroneId(@PathVariable String droneId) {
        List<Medication> loadedMedicationByDroneId = logisticService.getLoadedMedicationByDroneId(droneId);
        return ResponseEntity.ok(loadedMedicationByDroneId);
    }

    //todo available drones

    @GetMapping("/drones/available")
    public ResponseEntity<List<Drone>> getAvailableDronesForLoading(){
        return null;
    }

    //todo  remove the drone id and  drone state to idle  == return drone

}
