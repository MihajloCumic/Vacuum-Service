package com.example.vacuum_service.repository;

import com.example.vacuum_service.entities.Vacuum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {
}
