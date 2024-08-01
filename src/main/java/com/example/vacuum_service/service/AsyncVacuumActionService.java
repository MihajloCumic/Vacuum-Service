package com.example.vacuum_service.service;


import com.example.vacuum_service.entities.Vacuum;

public interface AsyncVacuumActionService {
    void startVacuumAsync(Vacuum vacuum);
    void stopVacuumAsync(Vacuum vacuum);

}
