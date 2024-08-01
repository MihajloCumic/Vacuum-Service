package com.example.vacuum_service.mappers;
import com.example.vacuum_service.dto.VacuumDto;
import com.example.vacuum_service.entities.Vacuum;
import org.springframework.stereotype.Component;

@Component
public class VacuumDtoMapper {
    public VacuumDto vacuumToDto(Vacuum vacuum){
        VacuumDto vacuumDto = new VacuumDto();
        vacuumDto.setId(vacuum.getId());
        vacuumDto.setName(vacuum.getName());
        vacuumDto.setVacuumStatus(vacuum.getVacuumStatus());
        vacuumDto.setAddedBy(vacuum.getAddedBy());
        vacuumDto.setActive(vacuum.getActive());
        vacuumDto.setCreatedTimestamp(vacuum.getCreatedTimestamp());
        vacuumDto.setStartStopCount(vacuum.getStartStopCount());
        return vacuumDto;
    }
}
