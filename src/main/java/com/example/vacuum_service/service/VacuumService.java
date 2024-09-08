package com.example.vacuum_service.service;

import com.example.vacuum_service.dto.AddVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;

import java.util.List;

public interface VacuumService {
    List<VacuumDto> getAllVacuums(String email);
    VacuumDto addVacuum(AddVacuumDto addVacuumDto, String addedBY);
    List<VacuumDto> searchVacuums(SearchParamsDto searchParamsDto, String email);
    void removeVacuum(Long id, String email);
    boolean startVacuum(Long id);
    boolean stopVacuum(Long id);
    boolean dischargeVacuum(Long id);
}
