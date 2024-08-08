package com.example.vacuum_service.service.impl;

import com.example.vacuum_service.dto.ErrorMessageDto;
import com.example.vacuum_service.entities.ErrorMessage;
import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.mappers.ErrorMessageDtoMapper;
import com.example.vacuum_service.repository.ErrorMessageRepository;
import com.example.vacuum_service.repository.VacuumRepository;
import com.example.vacuum_service.service.ErrorMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorMessageServiceImpl implements ErrorMessageService {
    private final ErrorMessageRepository errorMessageRepository;
    private final ErrorMessageDtoMapper errorMessageDtoMapper;
    private final VacuumRepository vacuumRepository;

    @Override
    public List<ErrorMessageDto> getAllErrorsForVacuum(Long id) {
        Vacuum vacuum = vacuumRepository.findById(id).orElseThrow(() -> new RuntimeException("No vacuum with id: " + id));
        return errorMessageRepository.findAllByVacuum(vacuum)
                .stream()
                .map(errorMessageDtoMapper::errorMessageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createErrorMessage(Vacuum vacuum, String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setVacuum(vacuum);
        errorMessage.setMessage(message);
        System.out.println(message);
        errorMessageRepository.save(errorMessage);
        System.out.println("sacuvano");
    }
}
