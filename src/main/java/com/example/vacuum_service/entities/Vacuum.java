package com.example.vacuum_service.entities;

import com.example.vacuum_service.entities.enums.VacuumStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vacuum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VacuumStatus vacuumStatus;

    @Column(nullable = false)
    private Long addedBy;

    @Column(nullable = false)
    private Boolean active;
}
