package com.example.vacuum_service.controller;

import com.example.vacuum_service.dto.ErrorMessageDto;
import com.example.vacuum_service.security.CustomUserDetails;
import com.example.vacuum_service.service.ErrorMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/error")
public class ErrorMessageController {
    private final ErrorMessageService errorMessageService;

    @GetMapping("/get-all/{id}")
    public ResponseEntity<List<ErrorMessageDto>> getAllForVacuumId(@PathVariable Long id){
        return ResponseEntity.ok(errorMessageService.getAllErrorsForVacuum(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<ErrorMessageDto>> getAllForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
            return ResponseEntity.ok(errorMessageService.getAllForUser(email));
        }
        return ResponseEntity.badRequest().build();
    }
}
