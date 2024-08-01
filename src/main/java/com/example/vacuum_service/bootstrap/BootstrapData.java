package com.example.vacuum_service.bootstrap;

import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.entities.enums.VacuumStatus;
import com.example.vacuum_service.repository.VacuumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final VacuumRepository vacuumRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> names = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            names.add("vacuum-" + i);
        }

        Random random = new Random();
        for(String name: names){
            Vacuum vacuum = new Vacuum();
            vacuum.setName(name);
            vacuum.setAddedBy((long) (random.nextInt(3) + 1));
            vacuum.setVacuumStatus(VacuumStatus.STOPPED);
            vacuum.setActive(true);
            vacuumRepository.save(vacuum);
        }
    }
}
