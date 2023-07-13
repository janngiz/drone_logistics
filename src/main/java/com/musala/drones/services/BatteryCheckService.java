package com.musala.drones.services;

import com.musala.drones.entities.BatteryLevelLog;
import com.musala.drones.entities.Drone;
import com.musala.drones.repo.BatteryLevelLogRepository;
import com.musala.drones.repo.DroneRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatteryCheckService {
    private final DroneRepository droneRepository;
    private final BatteryLevelLogRepository batteryLevelLogRepository;

    public BatteryCheckService(DroneRepository droneRepository, BatteryLevelLogRepository batteryLevelLogRepository) {
        this.droneRepository = droneRepository;
        this.batteryLevelLogRepository = batteryLevelLogRepository;
    }

    @Scheduled(fixedRate = 60000) // run every 60 seconds
    public void checkBatteryLevels() {
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones) {
            BatteryLevelLog log = new BatteryLevelLog();
            log.setDroneSerialNumber(drone.getSerialNumber());
            log.setTimestamp(LocalDateTime.now());
            log.setBatteryLevel(drone.getBatteryCapacity());
            batteryLevelLogRepository.save(log);
        }
    }
}
