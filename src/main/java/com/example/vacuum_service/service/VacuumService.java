package com.example.vacuum_service.service;

import com.example.vacuum_service.dto.AddVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;

import java.util.List;

public interface VacuumService {
    List<VacuumDto> getAllVacuums();
    VacuumDto addVacuum(AddVacuumDto addVacuumDto);
    List<VacuumDto> searchVacuums(SearchParamsDto searchParamsDto);
    void removeVacuum(Long id);
    boolean startVacuum(Long id);
    boolean stopVacuum(Long id);
    boolean dischargeVacuum(Long id);
}
