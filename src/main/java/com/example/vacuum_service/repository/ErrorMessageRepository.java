package com.example.vacuum_service.repository;

import com.example.vacuum_service.entities.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {
}
