package com.example.vacuum_service.service.impl;

import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.entities.enums.VacuumStatus;
import com.example.vacuum_service.repository.VacuumRepository;
import com.example.vacuum_service.service.AsyncVacuumActionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncVacuumActionServiceImpl implements AsyncVacuumActionService {
    private final VacuumRepository vacuumRepository;

    @Async
    @Transactional
    @Override
    public void startVacuumAsync(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("No such id."));
        try {
            Thread.sleep(15000);
            vacuum.setVacuumStatus(VacuumStatus.RUNNING);
            vacuumRepository.save(vacuum);
            System.out.println("Sacuvao START");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    @Transactional
    @Override
    public void stopVacuumAsync(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("No such id."));
        try {
            Thread.sleep(15000);
            vacuum.setVacuumStatus(VacuumStatus.STOPPED);
            vacuumRepository.save(vacuum);
            System.out.println("Sacuvao STOP");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    @Transactional
    @Override
    public void dischargeVacuumAsync(Long id) {
        Vacuum vacuum = vacuumRepository.findByIdLocking(id).orElseThrow(() -> new RuntimeException("No such id."));
        try {
            Thread.sleep(15000);
            vacuum.setVacuumStatus(VacuumStatus.DISCHARGING);
            vacuumRepository.save(vacuum);
            Thread.sleep(15000);
            vacuum.setVacuumStatus(VacuumStatus.STOPPED);
            vacuumRepository.save(vacuum);
            System.out.println("Sacuvao DISCHARGE");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
