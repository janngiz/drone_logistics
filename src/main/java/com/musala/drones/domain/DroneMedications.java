package com.musala.drones.domain;

import com.musala.drones.entities.Drone;
import com.musala.drones.entities.Medication;

import java.util.List;

public class DroneMedications {
    private Drone drone;
    private List<Medication> medicationList;


    public DroneMedications(Drone drone, List<Medication> medicationList) {
        this.drone = drone;
        this.medicationList = medicationList;
    }

    public DroneMedications() {
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }
}
