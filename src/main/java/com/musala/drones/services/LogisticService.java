package com.musala.drones.services;

import com.musala.drones.domain.DroneMedications;
import com.musala.drones.domain.DroneState;
import com.musala.drones.entities.Drone;
import com.musala.drones.entities.Medication;
import com.musala.drones.exceptions.ValidationException;
import com.musala.drones.repo.DroneRepository;
import com.musala.drones.repo.MedicationRepository;
import com.musala.drones.utils.LogisticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticService {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public DroneMedications loadMedicationsOnDrone(String droneId, List<String> medicationIds) {
        Drone drone = LogisticUtils.getDrone(droneRepository, droneId);
        if (medicationIds.isEmpty()) {
            throw new ValidationException("Atleast one medication.");
        }

        List<Medication> medicationsByIds = getMedicationsByIdAndIsDeliveredFalse(medicationIds);
        if (medicationsByIds.isEmpty()) {
            throw new ValidationException("Any medication can't be found by ids: " + String.join(",", medicationIds));
        }
        List<Medication> loadedMedications = addDroneOnMedication(droneId, medicationsByIds);
        drone.setState(DroneState.LOADED);
        Drone loadedDrone = droneRepository.save(drone);
        return new DroneMedications(loadedDrone, loadedMedications);


    }
    public DroneMedications deliverMedication(String droneId) {
        //check if the drone is in the loaded state or not
        Drone drone = LogisticUtils.getDrone(droneRepository, droneId);
        if (drone.getState() != DroneState.LOADED) {
            throw new ValidationException("Drone  is not in loaded state to proceed to deliver: " + droneId);

        }
        List<Medication> byDroneId = medicationRepository.findByDroneId(droneId);
        List<Medication> deliveredMedications = setMedicationToDelivered(byDroneId);
        return new DroneMedications(drone, deliveredMedications);
    }

    //Fetching only medications which are not delivered
    public List<Medication> getMedicationsByIdAndIsDeliveredFalse(List<String> ids) {
        return medicationRepository.findAllByIdAndIsDeliveredFalse(ids);
    }

    //associating the drones id with all medications
    public List<Medication> addDroneOnMedication(String droneId, List<Medication> medicationList) {
        medicationList.forEach(x -> x.setDroneId(droneId));
        return medicationRepository.saveAll(medicationList);
    }

    public List<Medication> setMedicationToDelivered(List<Medication> medicationList) {
        medicationList.forEach(x -> x.setDelivered(true));
        return medicationRepository.saveAll(medicationList);
    }


}
