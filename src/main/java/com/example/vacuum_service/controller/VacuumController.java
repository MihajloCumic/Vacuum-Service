package com.example.vacuum_service.controller;

import com.example.vacuum_service.dto.CreateVacuumDto;
import com.example.vacuum_service.dto.VacuumDto;
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
    public ResponseEntity<List<VacuumDto>> getAllVacuums(){
        return ResponseEntity.ok(vacuumService.getAllVacuums());
    }

    @GetMapping("/search")
    public ResponseEntity<List<VacuumDto>> searchVacuums(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<VacuumDto> addVacuum(@RequestBody CreateVacuumDto createVacuumDto){
        return ResponseEntity.ok(vacuumService.addVacuum(createVacuumDto));
    }

}
