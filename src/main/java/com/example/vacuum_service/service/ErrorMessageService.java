package com.example.vacuum_service.service;


import com.example.vacuum_service.dto.ErrorMessageDto;
import com.example.vacuum_service.entities.Vacuum;

import java.util.List;

public interface ErrorMessageService {

    List<ErrorMessageDto> getAllErrorsForVacuum(Long id);
    List<ErrorMessageDto> getAllForUser(String email);
    void createErrorMessage(Vacuum vacuum, String message);

}
