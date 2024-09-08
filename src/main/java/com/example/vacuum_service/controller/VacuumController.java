package com.example.vacuum_service.controller;

import com.example.vacuum_service.dto.AddVacuumDto;
import com.example.vacuum_service.dto.SearchParamsDto;
import com.example.vacuum_service.dto.VacuumDto;
import com.example.vacuum_service.security.CustomUserDetails;
import com.example.vacuum_service.service.VacuumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
            return ResponseEntity.ok(vacuumService.getAllVacuums(email));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('can_search_vacuums')")
    @PostMapping("/search")
    public ResponseEntity<List<VacuumDto>> searchVacuums(@RequestBody SearchParamsDto searchParamsDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
            return ResponseEntity.ok(vacuumService.searchVacuums(searchParamsDto, email));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('can_add_vacuums')")
    @PostMapping("/add")
    public ResponseEntity<VacuumDto> addVacuum(@Valid @RequestBody AddVacuumDto addVacuumDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
            return ResponseEntity.ok(vacuumService.addVacuum(addVacuumDto, email));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('can_start_vacuums')")
    @PostMapping("/start/{id}")
    public ResponseEntity<Void> startVacuum(@PathVariable Long id){
        boolean success = vacuumService.startVacuum(id);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.internalServerError().build();
    }

    @PreAuthorize("hasAuthority('can_stop_vacuums')")
    @PostMapping("/stop/{id}")
    public ResponseEntity<Void> stopVacuum(@PathVariable Long id){
        boolean success = vacuumService.stopVacuum(id);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.internalServerError().build();
    }

    @PreAuthorize("hasAuthority('can_discharge_vacuums')")
    @PostMapping("/discharge/{id}")
    public ResponseEntity<Void> dischargeVacuum(@PathVariable Long id){
        boolean success = vacuumService.dischargeVacuum(id);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.internalServerError().build();
    }

    @PreAuthorize("hasAuthority('can_remove_vacuums')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeVacuum(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
            vacuumService.removeVacuum(id, email);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
