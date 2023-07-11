package com.musala.drones.services;

import com.musala.drones.entities.Drone;
import com.musala.drones.exceptions.CustomException;
import com.musala.drones.repo.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    public Drone saveDrone(Drone drone) {

        if (drone.getSerialNumber() == null || drone.getSerialNumber().isEmpty()) {
            throw new CustomException("Drone serial number cannot be null or empty");
        }

        drone.setId(UUID.randomUUID().toString());

        return droneRepository.save(drone);
    }

}
