package com.example.vacuum_service.service;

import com.example.vacuum_service.dto.CreateVacuumDto;
import com.example.vacuum_service.entities.Vacuum;

import java.util.List;

public interface VacuumService {
    List<Vacuum> getAllVacuums();
    Vacuum addVacuum(CreateVacuumDto createVacuumDto);
}
