package com.musala.drones.repo;

import com.musala.drones.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findAllByIdAndIsDeliveredFalse(List<String> ids);

}
