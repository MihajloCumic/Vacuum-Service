package com.example.vacuum_service.service.impl;

import com.example.vacuum_service.dto.CreateVacuumDto;
import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.entities.enums.VacuumStatus;
import com.example.vacuum_service.repository.VacuumRepository;
import com.example.vacuum_service.service.VacuumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacuumServiceImpl implements VacuumService {
    private final VacuumRepository vacuumRepository;
    @Override
    public List<Vacuum> getAllVacuums() {
        return vacuumRepository.findAll();
    }

    @Override
    public Vacuum createVacuum(CreateVacuumDto createVacuumDto) {
        Vacuum vacuum = new Vacuum();
        vacuum.setVacuumStatus(VacuumStatus.OFF);
        vacuum.setAddedBy(createVacuumDto.getAddedBy());
        vacuum.setActive(createVacuumDto.getActive());
        return vacuumRepository.save(vacuum);
    }
}
