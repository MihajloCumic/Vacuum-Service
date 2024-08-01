package com.example.vacuum_service.repository;

import com.example.vacuum_service.entities.Vacuum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {

    List<Vacuum> findAllByActiveTrue();
    Optional<Vacuum> findByIdAndActiveTrue(Long id);

    @Query("SELECT v FROM Vacuum v WHERE " +
            "(:name IS NULL OR v.name LIKE %:name%) AND " +
            "(:statuses IS NULL OR v.vacuumStatus IN :statuses) AND " +
            "(:dateFrom IS NULL OR v.createdTimestamp >= :dateFrom) AND " +
            "(:dateTo IS NULL OR v.createdTimestamp <= :dateTo) AND " +
            "v.active IS TRUE")
    List<Vacuum> searchVacuums(@Param("name") String name,
                               @Param("statuses") List<String> statuses,
                               @Param("dateFrom") Long dateFrom,
                               @Param("dateTo") Long dateTo);
}
