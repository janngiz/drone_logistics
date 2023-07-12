package com.musala.drones.utils;

import com.musala.drones.entities.Drone;
import com.musala.drones.exceptions.ValidationException;
import com.musala.drones.repo.DroneRepository;

import java.util.Optional;

public class LogisticUtils {

    public static Drone getDrone(DroneRepository droneRepository, String droneId) {
        if (StringUtils.isNullOrEmpty(droneId)) {
            throw new ValidationException("Drone id cannot be null or empty");
        }
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);
        Drone drone;
        if (optionalDrone.isPresent()) {
            drone = optionalDrone.get();
        } else {
            throw new ValidationException("Drone not found with id: " + droneId);
        }

        return drone;
    }
}
