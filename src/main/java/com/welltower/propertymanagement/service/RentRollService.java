package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.RentRollDTO;
import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Resident;
import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.repository.PropertyRepository;
import com.welltower.propertymanagement.repository.ResidentRepository;
import com.welltower.propertymanagement.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentRollService {
    private final PropertyRepository propertyRepository;
    private final UnitRepository unitRepository;
    private final ResidentRepository residentRepository;

    public List<RentRollDTO> generateRentRoll(Long propertyId, LocalDate date) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        List<Unit> units = unitRepository.findByPropertyIdOrderByUnitNumber(propertyId);
        List<Resident> activeResidents = residentRepository.findActiveResidentsOnDate(propertyId, date);

        // Create a map for quick resident lookup by unit
        Map<Long, Resident> residentsByUnit = activeResidents.stream()
                .collect(Collectors.toMap(
                        r -> r.getUnit() != null ? r.getUnit().getUnitId() : null,
                        r -> r,
                        (r1, r2) -> r1 // In case of duplicates, keep the first
                ));

        List<RentRollDTO> rentRoll = new ArrayList<>();

        for (Unit unit : units) {
            if (!unit.getIsActive()) {
                continue; // Skip inactive units
            }

            Resident resident = residentsByUnit.get(unit.getUnitId());

            RentRollDTO rentRollEntry = RentRollDTO.builder()
                    .date(date)
                    .propertyId(property.getPropertyId())
                    .unitId(unit.getUnitId())
                    .unitNumber(unit.getUnitNumber())
                    .residentId(resident != null ? resident.getResidentId() : null)
                    .residentName(resident != null ? 
                            resident.getFirstName() + " " + resident.getLastName() : null)
                    .monthlyRent(resident != null ? resident.getMonthlyRent() : BigDecimal.ZERO)
                    .build();

            rentRoll.add(rentRollEntry);
        }

        return rentRoll;
    }

    public List<RentRollDTO> generateRentRollForDateRange(Long propertyId, LocalDate startDate, LocalDate endDate) {
        List<RentRollDTO> rentRoll = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            rentRoll.addAll(generateRentRoll(propertyId, date));
        }

        return rentRoll;
    }

    public Map<String, Object> generateRentRollSummary(Long propertyId, LocalDate date) {
        List<RentRollDTO> rentRoll = generateRentRoll(propertyId, date);

        long occupiedUnits = rentRoll.stream()
                .filter(r -> r.getResidentId() != null)
                .count();

        long vacantUnits = rentRoll.stream()
                .filter(r -> r.getResidentId() == null)
                .count();

        BigDecimal totalMonthlyRevenue = rentRoll.stream()
                .map(RentRollDTO::getMonthlyRent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double occupancyRate = rentRoll.isEmpty() ? 0 : (double) occupiedUnits / rentRoll.size() * 100;

        Map<String, Object> summary = new HashMap<>();
        summary.put("date", date);
        summary.put("property_id", propertyId);
        summary.put("total_units", rentRoll.size());
        summary.put("occupied_units", occupiedUnits);
        summary.put("vacant_units", vacantUnits);
        summary.put("occupancy_rate", String.format("%.2f%%", occupancyRate));
        summary.put("total_monthly_revenue", totalMonthlyRevenue);

        return summary;
    }
}
