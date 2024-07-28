package com.example.vacuum_service.service;

import com.example.vacuum_service.dto.CreateVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;
import com.example.vacuum_service.entities.Vacuum;

import java.util.List;

public interface VacuumService {
    List<VacuumDto> getAllVacuums();
    VacuumDto addVacuum(CreateVacuumDto createVacuumDto);
    List<VacuumDto> searchVacuums(SearchParamsDto searchParamsDto);
}
