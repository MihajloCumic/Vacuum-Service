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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacuumServiceImpl implements VacuumService {
    private final VacuumRepository vacuumRepository;
    private final VacuumDtoMapper vacuumDtoMapper;
    private final AsyncVacuumActionService asyncVacuumActionService;
    private final ErrorMessageService errorMessageService;
    @Override
    public List<VacuumDto> getAllVacuums() {
        return vacuumRepository.findAllByActiveTrue().stream().map(vacuumDtoMapper::vacuumToDto).collect(Collectors.toList());
    }

    @Override
    public VacuumDto addVacuum(AddVacuumDto addVacuumDto) {
        Vacuum vacuum = new Vacuum();
        vacuum.setName(addVacuumDto.getName());
        vacuum.setVacuumStatus(VacuumStatus.STOPPED);
        vacuum.setAddedBy(1L);
        vacuum.setActive(true);
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

    @Override
    public void removeVacuum(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new RuntimeException("Vacuum with id: " + id + "does not exist."));
        if(vacuum.getVacuumStatus() != VacuumStatus.STOPPED) throw new RuntimeException("Vacuum cannot be removed, because it is not STOPPED.");
        vacuum.setActive(false);
        vacuumRepository.save(vacuum);
    }

    @Transactional
    @Override
    public boolean startVacuum(Long id) {
        Optional<Vacuum> vacuumOptional = vacuumRepository.findByIdLocking(id);
        if(vacuumOptional.isEmpty()) return false;
        System.out.println("Proso lock START");
        Vacuum vacuum = vacuumOptional.get();
        if(vacuum.getVacuumStatus() != VacuumStatus.STOPPED){
            errorMessageService.createErrorMessage(vacuum, "Vacuum cannot be started because it is not STOPPED.");
            return false;
        }
        asyncVacuumActionService.startVacuumAsync(id);
        return true;
    }

    @Transactional
    @Override
    public boolean stopVacuum(Long id) {
        Optional<Vacuum> vacuumOptional = vacuumRepository.findByIdLocking(id);
        if(vacuumOptional.isEmpty()) return false;
        System.out.println("Proso lock STOP");
        Vacuum vacuum = vacuumOptional.get();
        if(vacuum.getVacuumStatus() != VacuumStatus.RUNNING){
            errorMessageService.createErrorMessage(vacuum, "Vacuum cannot be stopped because it is not RUNNING.");
            return false;
        }
        asyncVacuumActionService.stopVacuumAsync(id);
        return true;
    }

    @Transactional
    @Override
    public boolean dischargeVacuum(Long id) {
        Optional<Vacuum> vacuumOptional = vacuumRepository.findByIdLocking(id);
        if(vacuumOptional.isEmpty()) return false;
        System.out.println("Proso lock DISCHARGE");
        Vacuum vacuum = vacuumOptional.get();
        if(vacuum.getVacuumStatus() != VacuumStatus.STOPPED){
            errorMessageService.createErrorMessage(vacuum, "Vacuum cannot be discharged because it is not STOPPED.");
            return false;
        }
        asyncVacuumActionService.dischargeVacuumAsync(id);
        return true;
    }
}
