package com.musala.drones.controllers;

import com.musala.drones.entities.Drone;
import com.musala.drones.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/drone")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> saveDrone(@RequestBody Drone drone) {
        Drone savedDrone = droneService.saveDrone(drone);
        return ResponseEntity.ok(savedDrone);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> getDroneById(@PathVariable String id) {
        Drone droneById = droneService.getDroneById(id);
        return ResponseEntity.ok(droneById);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Drone>> getAllDrones() {
        List<Drone> drones = droneService.getAllDrones();
        return ResponseEntity.ok(drones);
    }


    @PutMapping(value = "/{id}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> updateDrone(@PathVariable String id, @RequestBody Drone updatedDrone) {
        Drone drone = droneService.updateDrone(id, updatedDrone);
        return ResponseEntity.ok(drone);
    }


}
