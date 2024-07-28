package com.example.vacuum_service.service.impl;

import com.example.vacuum_service.dto.CreateVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;
import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.entities.enums.VacuumStatus;
import com.example.vacuum_service.mappers.VacuumDtoMapper;
import com.example.vacuum_service.repository.VacuumRepository;
import com.example.vacuum_service.service.VacuumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacuumServiceImpl implements VacuumService {
    private final VacuumRepository vacuumRepository;
    private final VacuumDtoMapper vacuumDtoMapper;
    @Override
    public List<VacuumDto> getAllVacuums() {
        return vacuumRepository.findAll().stream().map(vacuumDtoMapper::vacuumToDto).collect(Collectors.toList());
    }

    @Override
    public VacuumDto addVacuum(CreateVacuumDto createVacuumDto) {
        Vacuum vacuum = new Vacuum();
        vacuum.setName(createVacuumDto.getName());
        vacuum.setVacuumStatus(VacuumStatus.STOPPED);
        vacuum.setAddedBy(createVacuumDto.getAddedBy());
        vacuum.setActive(createVacuumDto.getActive());
        return vacuumDtoMapper.vacuumToDto(vacuumRepository.save(vacuum));
    }

    @Override
    public List<VacuumDto> searchVacuums(SearchParamsDto searchParamsDto) throws RuntimeException{
        if(searchParamsDto.getDateFrom() == null && searchParamsDto.getDateTo() != null) throw new RuntimeException("There must be dateFrom cannot be blank if dateTo has a value.");
        for(String status: searchParamsDto.getStatuses()){
            VacuumStatus.valueOf(status.toUpperCase());
        }
        return vacuumRepository.searchVacuums(searchParamsDto.getName(), searchParamsDto.getStatuses(), searchParamsDto.getDateFrom(), searchParamsDto.getDateTo())
                .stream().map(vacuumDtoMapper::vacuumToDto).collect(Collectors.toList());
    }
}
