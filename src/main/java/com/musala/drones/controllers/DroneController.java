package com.musala.drones.controllers;

import com.musala.drones.entities.Drone;
import com.musala.drones.exceptions.CustomException;
import com.musala.drones.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/drone")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/save")
    public ResponseEntity<Drone> saveDrone(@RequestBody Drone drone) {
        Drone savedDrone = droneService.saveDrone(drone);
        return ResponseEntity.ok(savedDrone);
    }

}
