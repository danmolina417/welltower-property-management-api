package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.model.Resident;
import com.welltower.propertymanagement.repository.PropertyRepository;
import com.welltower.propertymanagement.repository.UnitRepository;
import com.welltower.propertymanagement.repository.ResidentRepository;
import com.welltower.propertymanagement.dto.RentRollDTO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Disabled
@ExtendWith(MockitoExtension.class)
class RentRollServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private ResidentRepository residentRepository;

    @InjectMocks
    private RentRollService rentRollService;

    @Test
    void testGenerateRentRoll() {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        property.setPropertyName("Test Property");

        Unit unit1 = new Unit();
        unit1.setId(1L);
        unit1.setProperty(property);
        unit1.setIsActive(true);
        unit1.setUnitNumber("101");

        Unit unit2 = new Unit();
        unit2.setId(2L);
        unit2.setProperty(property);
        unit2.setIsActive(true);
        unit2.setUnitNumber("102");

        Resident resident = new Resident();
        resident.setId(1L);
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setMonthlyRent(new BigDecimal("1200.00"));
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setIsActive(true);
        resident.setUnit(unit1);
        resident.setProperty(property);

        LocalDate date = LocalDate.of(2024, 3, 15);

        when(propertyRepository.findById(1L)).thenReturn(java.util.Optional.of(property));
        when(unitRepository.findByPropertyIdOrderByUnitNumber(1L)).thenReturn(Arrays.asList(unit1, unit2));
        when(residentRepository.findActiveResidentsOnDate(1L, date)).thenReturn(Arrays.asList(resident));

        // Act
        List<RentRollDTO> rentRoll = rentRollService.generateRentRoll(1L, date);

        // Assert
        assertEquals(2, rentRoll.size());

        // Check occupied unit
        RentRollDTO occupiedUnit = rentRoll.stream()
                .filter(r -> r.getUnitId().equals(1L))
                .findFirst().orElse(null);
        assertNotNull(occupiedUnit);
        assertEquals("101", occupiedUnit.getUnitNumber());
        assertEquals(1L, occupiedUnit.getResidentId());
        assertEquals("John Doe", occupiedUnit.getResidentName());
        assertEquals(new BigDecimal("1200.00"), occupiedUnit.getMonthlyRent());

        // Check vacant unit
        RentRollDTO vacantUnit = rentRoll.stream()
                .filter(r -> r.getUnitId().equals(2L))
                .findFirst().orElse(null);
        assertNotNull(vacantUnit);
        assertEquals("102", vacantUnit.getUnitNumber());
        assertNull(vacantUnit.getResidentId());
        assertNull(vacantUnit.getResidentName());
        assertEquals(BigDecimal.ZERO, vacantUnit.getMonthlyRent());
    }

    @Test
    void testGenerateRentRollSummary() {
        // Arrange
        Property property = new Property();
        property.setId(1L);

        Unit unit1 = new Unit();
        unit1.setId(1L);
        unit1.setProperty(property);
        unit1.setIsActive(true);

        Unit unit2 = new Unit();
        unit2.setId(2L);
        unit2.setProperty(property);
        unit2.setIsActive(true);

        Unit unit3 = new Unit();
        unit3.setId(3L);
        unit3.setProperty(property);
        unit3.setIsActive(true);

        Resident resident1 = new Resident();
        resident1.setMonthlyRent(new BigDecimal("1200.00"));
        resident1.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident1.setIsActive(true);
        resident1.setUnit(unit1);
        resident1.setProperty(property);

        Resident resident2 = new Resident();
        resident2.setMonthlyRent(new BigDecimal("1300.00"));
        resident2.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident2.setIsActive(true);
        resident2.setUnit(unit2);
        resident2.setProperty(property);

        LocalDate date = LocalDate.of(2024, 3, 15);

        when(propertyRepository.findById(1L)).thenReturn(java.util.Optional.of(property));
        when(unitRepository.findByPropertyIdOrderByUnitNumber(1L)).thenReturn(Arrays.asList(unit1, unit2, unit3));
        when(residentRepository.findActiveResidentsOnDate(1L, date)).thenReturn(Arrays.asList(resident1, resident2));

        // Act
        Map<String, Object> summary = rentRollService.generateRentRollSummary(1L, date);

        // Assert
        assertEquals(date, summary.get("date"));
        assertEquals(1L, summary.get("property_id"));
        assertEquals(3, summary.get("total_units"));
        assertEquals(2L, summary.get("occupied_units"));
        assertEquals(1L, summary.get("vacant_units"));
        assertEquals("66.67%", summary.get("occupancy_rate"));
        assertEquals(new BigDecimal("2500.00"), summary.get("total_monthly_revenue"));
    }

    @Test
    void testGenerateRentRollSummariesForDateRange() {
        // Arrange
        Property property = new Property();
        property.setId(1L);

        Unit unit = new Unit();
        unit.setId(1L);
        unit.setProperty(property);
        unit.setIsActive(true);

        Resident resident = new Resident();
        resident.setMonthlyRent(new BigDecimal("1200.00"));
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setIsActive(true);
        resident.setUnit(unit);
        resident.setProperty(property);

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 3, 3);

        when(propertyRepository.findById(1L)).thenReturn(java.util.Optional.of(property));
        when(unitRepository.findByPropertyIdOrderByUnitNumber(1L)).thenReturn(Arrays.asList(unit));
        when(residentRepository.findActiveResidentsOnDate(1L, startDate)).thenReturn(Arrays.asList(resident));
        when(residentRepository.findActiveResidentsOnDate(1L, startDate.plusDays(1))).thenReturn(Arrays.asList(resident));
        when(residentRepository.findActiveResidentsOnDate(1L, endDate)).thenReturn(Arrays.asList(resident));

        // Act
        List<Map<String, Object>> summaries = rentRollService.generateRentRollSummariesForDateRange(1L, startDate, endDate);

        // Assert
        assertEquals(3, summaries.size()); // 3 days: 1st, 2nd, 3rd

        for (Map<String, Object> summary : summaries) {
            assertEquals(1L, summary.get("property_id"));
            assertEquals(1, summary.get("total_units"));
            assertEquals(1L, summary.get("occupied_units"));
            assertEquals(0L, summary.get("vacant_units"));
            assertEquals("100.00%", summary.get("occupancy_rate"));
            assertEquals(new BigDecimal("1200.00"), summary.get("total_monthly_revenue"));
        }

        // Check specific dates
        assertEquals(startDate, summaries.get(0).get("date"));
        assertEquals(startDate.plusDays(1), summaries.get(1).get("date"));
        assertEquals(endDate, summaries.get(2).get("date"));
    }

    @Test
    void testGenerateRentRollForDateRange() {
        // Arrange
        Property property = new Property();
        property.setId(1L);

        Unit unit = new Unit();
        unit.setId(1L);
        unit.setProperty(property);
        unit.setIsActive(true);
        unit.setUnitNumber("101");

        Resident resident = new Resident();
        resident.setId(1L);
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setMonthlyRent(new BigDecimal("1200.00"));
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setIsActive(true);
        resident.setUnit(unit);
        resident.setProperty(property);

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 3, 2);

        when(propertyRepository.findById(1L)).thenReturn(java.util.Optional.of(property));
        when(unitRepository.findByPropertyIdOrderByUnitNumber(1L)).thenReturn(Arrays.asList(unit));
        when(residentRepository.findActiveResidentsOnDate(eq(1L), any(LocalDate.class))).thenReturn(Arrays.asList(resident));

        // Act
        List<RentRollDTO> rentRoll = rentRollService.generateRentRollForDateRange(1L, startDate, endDate);

        // Assert
        assertEquals(2, rentRoll.size()); // One entry for each date

        for (RentRollDTO dto : rentRoll) {
            assertEquals(1L, dto.getUnitId());
            assertEquals("101", dto.getUnitNumber());
            assertEquals(1L, dto.getResidentId());
            assertEquals("John Doe", dto.getResidentName());
            assertEquals(new BigDecimal("1200.00"), dto.getMonthlyRent());
        }
    }
}