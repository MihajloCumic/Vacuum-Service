package com.example.vacuum_service.entities;

import com.example.vacuum_service.entities.enums.VacuumStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private Instant createdTimestamp;
}
