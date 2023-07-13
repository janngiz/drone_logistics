package com.musala.drones.repo;

import com.musala.drones.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findAllByIsDeliveredFalseAndIdIn(List<String> ids);

    @Query("SELECT m FROM Medication m WHERE m.isDelivered = false AND (m.droneId IS NULL OR m.droneId = '')")
    List<Medication> findAllByIsDeliveredFalseAndDroneIdIsNullOrIsEmpty();
    List<Medication> findByDroneIdAndIsDeliveredFalse(String droneId);


}
