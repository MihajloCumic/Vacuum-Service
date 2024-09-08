package com.example.vacuum_service.service.impl;

import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.entities.enums.VacuumStatus;
import com.example.vacuum_service.repository.VacuumRepository;
import com.example.vacuum_service.service.AsyncVacuumActionService;
import com.example.vacuum_service.service.ErrorMessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AsyncVacuumActionServiceImpl implements AsyncVacuumActionService {
    private final VacuumRepository vacuumRepository;
    private final ErrorMessageService errorMessageService;

    @Async
    @Transactional
    @Override
    public void startVacuumAsync(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("No such id."));
        if(vacuum.getVacuumStatus() != VacuumStatus.STOPPED){
            errorMessageService.createErrorMessage(vacuum, "Vacuum cannot be started because it is not in state STOPPED.");
            return;
        }
        try {
            Thread.sleep(15000 + new Random().nextInt(3000));
            vacuum.setVacuumStatus(VacuumStatus.RUNNING);
            vacuumRepository.save(vacuum);
        } catch (InterruptedException e) {
            errorMessageService.createErrorMessage(vacuum, "Server error.");
            throw new RuntimeException(e);
        }
    }

    @Async
    @Transactional
    @Override
    public void stopVacuumAsync(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("No such id."));
        if(vacuum.getVacuumStatus() != VacuumStatus.RUNNING){
            errorMessageService.createErrorMessage(vacuum, "Vacuum cannot be stopped because it is not in state RUNNING.");
            return;
        }
        try {
            Thread.sleep(15000 + new Random().nextInt(3000));
            vacuum.setVacuumStatus(VacuumStatus.STOPPED);
            vacuum.setStartStopCount(vacuum.getStartStopCount() + 1);
            if(vacuum.getStartStopCount() == 3){
                Thread.sleep(15000 + new Random().nextInt(3000));
                vacuum.setVacuumStatus(VacuumStatus.DISCHARGING);
                vacuumRepository.save(vacuum);
                Thread.sleep(15000 + new Random().nextInt(3000));
                vacuum.setVacuumStatus(VacuumStatus.STOPPED);
                vacuum.setStartStopCount(0);
            }
            vacuumRepository.save(vacuum);
        } catch (InterruptedException e) {
            errorMessageService.createErrorMessage(vacuum, "Server error.");
            throw new RuntimeException(e);
        }
    }

    @Async
    @Transactional
    @Override
    public void dischargeVacuumAsync(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("No such id."));
        if(vacuum.getVacuumStatus() != VacuumStatus.STOPPED){
            errorMessageService.createErrorMessage(vacuum, "Vacuum cannot be discharged because it is not STOPPED.");
            return;
        }
        try {
            Thread.sleep(15000 + new Random().nextInt(3000));
            vacuum.setVacuumStatus(VacuumStatus.DISCHARGING);
            vacuumRepository.save(vacuum);
            Thread.sleep(15000 + new Random().nextInt(3000));
            vacuum.setVacuumStatus(VacuumStatus.STOPPED);
            vacuum.setStartStopCount(0);
            vacuumRepository.save(vacuum);
        } catch (InterruptedException e) {
            errorMessageService.createErrorMessage(vacuum, "Server error.");
            throw new RuntimeException(e);
        }
    }
}
