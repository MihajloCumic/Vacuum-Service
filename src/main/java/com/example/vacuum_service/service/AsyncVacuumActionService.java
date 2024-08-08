package com.example.vacuum_service.service;


import com.example.vacuum_service.entities.Vacuum;

public interface AsyncVacuumActionService {
    void startVacuumAsync(Long id);
    void stopVacuumAsync(Long id);
    void dischargeVacuumAsync(Long id);

}
