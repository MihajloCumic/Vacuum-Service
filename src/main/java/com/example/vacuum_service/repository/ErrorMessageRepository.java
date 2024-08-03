package com.example.vacuum_service.repository;

import com.example.vacuum_service.entities.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {
    List<ErrorMessage> findAllByVacuumId(Long id);
}
