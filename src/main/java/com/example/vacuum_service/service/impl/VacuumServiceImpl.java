package com.example.vacuum_service.service.impl;

import com.example.vacuum_service.dto.AddVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;
import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.entities.enums.VacuumStatus;
import com.example.vacuum_service.mappers.VacuumDtoMapper;
import com.example.vacuum_service.repository.VacuumRepository;
import com.example.vacuum_service.service.AsyncVacuumActionService;
import com.example.vacuum_service.service.ErrorMessageService;
import com.example.vacuum_service.service.VacuumService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacuumServiceImpl implements VacuumService {
    private final VacuumRepository vacuumRepository;
    private final VacuumDtoMapper vacuumDtoMapper;
    private final AsyncVacuumActionService asyncVacuumActionService;
    @Override
    public List<VacuumDto> getAllVacuums(String email) {
        return vacuumRepository.findAllByAddedByAndActiveTrue(email).stream().map(vacuumDtoMapper::vacuumToDto).collect(Collectors.toList());
    }

    @Override
    public VacuumDto addVacuum(AddVacuumDto addVacuumDto, String addedBy) {
        Vacuum vacuum = new Vacuum();
        vacuum.setName(addVacuumDto.getName());
        vacuum.setVacuumStatus(VacuumStatus.STOPPED);
        vacuum.setAddedBy(addedBy);
        vacuum.setActive(true);
        return vacuumDtoMapper.vacuumToDto(vacuumRepository.save(vacuum));
    }

    @Override
    public List<VacuumDto> searchVacuums(SearchParamsDto searchParamsDto, String email) throws RuntimeException{
        if(searchParamsDto.getDateFrom() == null && searchParamsDto.getDateTo() != null) throw new RuntimeException("There must be dateFrom cannot be blank if dateTo has a value.");
        for(String status: searchParamsDto.getStatuses()){
            VacuumStatus.valueOf(status.toUpperCase());
        }
        return vacuumRepository.searchVacuums(searchParamsDto.getName(), searchParamsDto.getStatuses(), searchParamsDto.getDateFrom(), searchParamsDto.getDateTo(), email)
                .stream().map(vacuumDtoMapper::vacuumToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void removeVacuum(Long id, String email) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("Vacuum with id: " + id + "does not exist."));
        if(!vacuum.getAddedBy().equals(email)) throw new RuntimeException("Vacuum ownership error.");
        if(vacuum.getVacuumStatus() != VacuumStatus.STOPPED) throw new RuntimeException("Vacuum cannot be removed, because it is not STOPPED.");
        vacuum.setActive(false);
        vacuumRepository.save(vacuum);
    }

    @Transactional
    @Override
    public boolean startVacuum(Long id) {
        asyncVacuumActionService.startVacuumAsync(id);
        return true;

    }

    @Transactional
    @Override
    public boolean stopVacuum(Long id) {
        asyncVacuumActionService.stopVacuumAsync(id);
        return true;
    }

    @Transactional
    @Override
    public boolean dischargeVacuum(Long id) {
        asyncVacuumActionService.dischargeVacuumAsync(id);
        return true;
    }
}
