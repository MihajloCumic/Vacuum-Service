package com.example.vacuum_service.dto;

import com.example.vacuum_service.entities.enums.VacuumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacuumDto {
    private Long id;
    private String name;
    private VacuumStatus vacuumStatus;
    private Long addedBy;
    private Boolean active;
    private Long createdTimestamp;
    private Integer startStopCount;
}
