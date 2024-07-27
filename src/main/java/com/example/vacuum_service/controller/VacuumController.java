package com.example.vacuum_service.controller;

import com.example.vacuum_service.dto.CreateVacuumDto;
import com.example.vacuum_service.entities.Vacuum;
import com.example.vacuum_service.service.VacuumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "api/vacuum")
public class VacuumController {
    private final VacuumService vacuumService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Vacuum>> getAllVacuums(){
        return ResponseEntity.ok(vacuumService.getAllVacuums());
    }

    @PostMapping("/create")
    public ResponseEntity<Vacuum> createVacuum(@RequestBody CreateVacuumDto createVacuumDto){
        return ResponseEntity.ok(vacuumService.createVacuum(createVacuumDto));
    }

}
