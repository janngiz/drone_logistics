package com.musala.drones.repo;

import com.musala.drones.domain.DroneState;
import com.musala.drones.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findByState(DroneState state);

    long count();

}
