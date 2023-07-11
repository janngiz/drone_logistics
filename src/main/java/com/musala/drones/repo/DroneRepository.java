package com.musala.drones.repo;

import com.musala.drones.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, String> {
}
