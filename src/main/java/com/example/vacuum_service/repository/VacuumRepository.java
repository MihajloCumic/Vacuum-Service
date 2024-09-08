package com.example.vacuum_service.repository;

import com.example.vacuum_service.entities.Vacuum;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {

    List<Vacuum> findAllByActiveTrue();
    List<Vacuum> findAllByAddedByAndActiveTrue(String addedBy);
    Optional<Vacuum> findByIdAndActiveTrue(Long id);
    Optional<Vacuum> findByIdAndAddedByAndActiveTrue(Long id, String addedBy);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM Vacuum v WHERE v.id = :id AND v.active IS TRUE")
    Optional<Vacuum> findByIdLocking(@Param("id") Long id);

    @Query("SELECT v FROM Vacuum v WHERE " +
            "(v.addedBy = :addedBy) AND" +
            "(:name IS NULL OR v.name LIKE %:name%) AND " +
            "(:statuses IS NULL OR v.vacuumStatus IN :statuses) AND " +
            "(:dateFrom IS NULL OR v.createdTimestamp >= :dateFrom) AND " +
            "(:dateTo IS NULL OR v.createdTimestamp <= :dateTo) AND " +
            "v.active IS TRUE")
    List<Vacuum> searchVacuums(@Param("name") String name,
                               @Param("statuses") List<String> statuses,
                               @Param("dateFrom") Long dateFrom,
                               @Param("dateTo") Long dateTo,
                                @Param("addedBy") String addedBy);
}
