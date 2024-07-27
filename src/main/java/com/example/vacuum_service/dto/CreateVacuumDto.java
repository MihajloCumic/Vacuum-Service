package com.example.vacuum_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVacuumDto {
    private Long addedBy;
    private Boolean active;
}
