package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findByPropertyIdOrderByUnitNumber(Long propertyId);

    List<Unit> findByPropertyIdAndIsActive(Long propertyId, Boolean isActive);

    Optional<Unit> findByPropertyIdAndUnitNumber(Long propertyId, String unitNumber);

    Integer countByPropertyIdAndIsOccupied(Long propertyId, Boolean isOccupied);
}
