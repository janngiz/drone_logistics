package com.musala.drones.services;

import com.musala.drones.entities.Drone;
import com.musala.drones.exceptions.ValidationException;
import com.musala.drones.repo.DroneRepository;
import com.musala.drones.utils.LogisticUtils;
import com.musala.drones.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    public Drone saveDrone(Drone drone) {
        if (StringUtils.isNullOrEmpty(drone.getSerialNumber())) {
            throw new ValidationException("Drone serial number cannot be null or empty");
        }
        drone.setId(UUID.randomUUID().toString());
        return droneRepository.save(drone);
    }

    public Drone getDroneById(String id) {
        return LogisticUtils.getDrone(droneRepository, id);
    }

    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    public Drone updateDrone(String id, Drone updatedDrone) {
        Drone drone = LogisticUtils.getDrone(droneRepository, id);
        drone.setSerialNumber(updatedDrone.getSerialNumber());
        drone.setModel(updatedDrone.getModel());
        drone.setWeightLimit(updatedDrone.getWeightLimit());
        drone.setBatteryCapacity(updatedDrone.getBatteryCapacity());
        drone.setState(updatedDrone.getState());
        return droneRepository.save(drone);
    }


}

