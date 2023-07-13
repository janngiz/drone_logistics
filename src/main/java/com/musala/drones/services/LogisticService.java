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
            throw new ValidationException("At least one medication required.");
        }

        if(drone.getState() != DroneState.IDLE){
            throw new ValidationException("Drone is not available to load or not in IDLE state, id:" + droneId);
        }

        //Prevent the drone from being in LOADING state if the battery level is below 25%;
        if (drone.getBatteryCapacity() < 25) {
            throw new ValidationException("Drone cannot be loaded below battery percentage 25.");
        }

        List<Medication> medicationsByIds = getMedicationsByIdAndIsDeliveredFalse(medicationIds);
        if (medicationsByIds.isEmpty()) {
            throw new ValidationException("Any medication can't be found by ids: " + String.join(",", medicationIds));
        }

        //Prevent the drone from being loaded with more weight that it can carry;
        if (getTotalWeightOfMedication(medicationsByIds) > drone.getWeightLimit()) {
            throw new ValidationException("Medication weight exceeds the drone weight limit. ");

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
        List<Medication> byDroneId = medicationRepository.findByDroneIdAndIsDeliveredFalse(droneId);
        List<Medication> deliveredMedications = setMedicationToDelivered(byDroneId);

        drone.setState(DroneState.DELIVERED);
        Drone deliveredDrone = droneRepository.save(drone);
        return new DroneMedications(deliveredDrone, deliveredMedications);
    }


    public List<Medication> getLoadedMedicationByDroneId(String droneId) {
        //check the drone exists
        LogisticUtils.getDrone(droneRepository, droneId);
        return medicationRepository.findByDroneIdAndIsDeliveredFalse(droneId);
    }


    // state = idle  drones, are consider as available drones
    public List<Drone> getAvailableDronesForLoading() {
        return droneRepository.findByState(DroneState.IDLE);

    }

    public List<Medication> getAvailableMedicationsForLoading() {
        return medicationRepository.findAllByIsDeliveredFalseAndDroneIdIsNullOrIsEmpty();

    }
    public int getBatteryOfDrone(String id) {
        Drone drone = LogisticUtils.getDrone(droneRepository, id);
        return drone.getBatteryCapacity();
    }

    //Fetching only medications which are not delivered
    public List<Medication> getMedicationsByIdAndIsDeliveredFalse(List<String> ids) {
        return medicationRepository.findAllByIsDeliveredFalseAndIdIn(ids);
    }

    //associating the drones id with all medications
    private List<Medication> addDroneOnMedication(String droneId, List<Medication> medicationList) {
        medicationList.forEach(x -> x.setDroneId(droneId));
        return medicationRepository.saveAll(medicationList);
    }

    private List<Medication> setMedicationToDelivered(List<Medication> medicationList) {
        medicationList.forEach(x -> x.setDelivered(true));
        return medicationRepository.saveAll(medicationList);
    }

    private double getTotalWeightOfMedication(List<Medication> medicationList) {
        return medicationList.stream().mapToDouble(Medication::getWeight).sum();
    }


}
