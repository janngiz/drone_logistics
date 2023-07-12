package com.musala.drones.services;

import com.musala.drones.domain.DroneMedications;
import com.musala.drones.domain.DroneState;
import com.musala.drones.entities.Drone;
import com.musala.drones.entities.Medication;
import com.musala.drones.exceptions.ValidationException;
import com.musala.drones.repo.DroneRepository;
import com.musala.drones.repo.MedicationRepository;
import com.musala.drones.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogisticService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;


    public DroneMedications loadMedicationsOnDrone(String droneId, List<String> medicationIds) {
        if (StringUtils.isNullOrEmpty(droneId)) {
            throw new ValidationException("Drone id cannot be null or empty");
        }

        if (medicationIds.isEmpty()) {
            throw new ValidationException("Atleast one medication.");
        }

        Optional<Drone> optionalDrone = droneRepository.findById(droneId);

        Drone drone;
        if (optionalDrone.isPresent()) {
            drone = optionalDrone.get();
        } else {
            throw new ValidationException("Drone not found with id: " + droneId);
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

    //Fetching only medications which are not delivered
    public List<Medication> getMedicationsByIdAndIsDeliveredFalse(List<String> ids) {
        return medicationRepository.findAllByIdAndIsDeliveredFalse(ids);
    }

    //associating the drones id with all medications
    public List<Medication> addDroneOnMedication(String droneId, List<Medication> medicationList) {
        medicationList.forEach(x -> x.setDroneId(droneId));
        return medicationRepository.saveAll(medicationList);
    }


}
