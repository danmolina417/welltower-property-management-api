package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByPropertyId(Long propertyId);

    List<Resident> findByUnitId(Long unitId);

    List<Resident> findByPropertyIdAndIsActive(Long propertyId, Boolean isActive);

    @Query("SELECT r FROM Resident r WHERE r.property.propertyId = :propertyId " +
           "AND r.moveInDate <= :date " +
           "AND (r.moveOutDate IS NULL OR r.moveOutDate > :date)")
    List<Resident> findActiveResidentsOnDate(@Param("propertyId") Long propertyId, 
                                             @Param("date") LocalDate date);

    Optional<Resident> findByUnitIdAndMoveOutDateIsNull(Long unitId);
}
