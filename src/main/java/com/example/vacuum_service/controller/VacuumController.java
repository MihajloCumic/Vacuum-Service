package com.example.vacuum_service.controller;

import com.example.vacuum_service.dto.AddVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;
import com.example.vacuum_service.service.VacuumService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<VacuumDto>> searchVacuums(@RequestBody SearchParamsDto searchParamsDto){
        return ResponseEntity.ok(vacuumService.searchVacuums(searchParamsDto));
    }

    @PostMapping("/add")
    public ResponseEntity<VacuumDto> addVacuum(@Valid @RequestBody AddVacuumDto addVacuumDto){
        return ResponseEntity.ok(vacuumService.addVacuum(addVacuumDto));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeVacuum(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }


}
