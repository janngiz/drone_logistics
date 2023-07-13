package com.musala.drones.repo;

import com.musala.drones.entities.BatteryLevelLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryLevelLogRepository extends JpaRepository<BatteryLevelLog, Long> {
}
