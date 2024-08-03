package com.example.vacuum_service.mappers;

import com.example.vacuum_service.dto.ErrorMessageDto;
import com.example.vacuum_service.entities.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorMessageDtoMapper {
    private final VacuumDtoMapper vacuumDtoMapper;
    public ErrorMessageDto errorMessageToDto(ErrorMessage errorMessage){
        return new ErrorMessageDto(errorMessage.getId(),
                vacuumDtoMapper.vacuumToDto(errorMessage.getVacuum()),
                errorMessage.getMessage(),
                errorMessage.getCreatedTimestamp());
    }
}
