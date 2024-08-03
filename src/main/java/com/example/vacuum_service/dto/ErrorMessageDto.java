package com.example.vacuum_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDto {

    private Long id;
    private VacuumDto vacuum;
    private String message;
    private Long createdTimestamp;
}
