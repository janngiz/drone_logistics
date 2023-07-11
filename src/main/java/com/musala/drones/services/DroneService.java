package com.musala.drones.services;

import com.musala.drones.entities.Drone;
import com.musala.drones.exceptions.NotFoundException;
import com.musala.drones.exceptions.ValidationException;
import com.musala.drones.repo.DroneRepository;
import com.musala.drones.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        if (StringUtils.isNullOrEmpty(id)) {
            throw new NotFoundException("Drone id cannot be null or empty");
        }
        Optional<Drone> optionalDrone = droneRepository.findById(id);
        if (optionalDrone.isPresent()) {
            return optionalDrone.get();
        } else {
            throw new NotFoundException("Drone not found with id: " + id);
        }
    }

    public List<Drone> getAllDrones(){
       return droneRepository.findAll();
    }

    public Drone updateDrone(String id, Drone updatedDrone) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new ValidationException("Drone id cannot be null or empty");
        }
        Optional<Drone> optionalDrone = droneRepository.findById(id);
        if (optionalDrone.isPresent()) {
            Drone drone = optionalDrone.get();
            drone.setSerialNumber(updatedDrone.getSerialNumber());
            drone.setModel(updatedDrone.getModel());
            drone.setWeightLimit(updatedDrone.getWeightLimit());
            drone.setBatteryCapacity(updatedDrone.getBatteryCapacity());
            drone.setState(updatedDrone.getState());

            return droneRepository.save(drone);
        } else {
                throw new NotFoundException("Drone not found with id: " + id);
        }
    }




}
