package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Resident;
import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class ResidentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResidentRepository residentRepository;

    @Test
    void testSaveAndFindResident() {
        // Create property and unit first
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setProperty(savedProperty);
        Unit savedUnit = entityManager.persistAndFlush(unit);

        // Create and save a resident
        Resident resident = new Resident();
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setEmail("john.doe@example.com");
        resident.setPhoneNumber("555-1234");
        resident.setMonthlyRent(new BigDecimal("1200.00"));
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setIsActive(true);
        resident.setProperty(savedProperty);
        resident.setUnit(savedUnit);

        Resident savedResident = residentRepository.save(resident);
        entityManager.flush();

        // Find the resident
        Optional<Resident> foundResident = residentRepository.findById(savedResident.getId());

        assertTrue(foundResident.isPresent());
        assertEquals("John", foundResident.get().getFirstName());
        assertEquals("Doe", foundResident.get().getLastName());
        assertEquals(savedUnit, foundResident.get().getUnit());
    }

    @Test
    void testFindByUnitId() {
        // Create property and unit
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setProperty(savedProperty);
        Unit savedUnit = entityManager.persistAndFlush(unit);

        // Create resident for the unit
        Resident resident = new Resident();
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setUnit(savedUnit);
        resident.setProperty(savedProperty);
        resident.setMonthlyRent(new BigDecimal("1200.00"));
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setIsActive(true);
        entityManager.persistAndFlush(resident);

        // Find residents by unit ID
        List<Resident> residents = residentRepository.findByUnitId(savedUnit.getId());

        assertEquals(1, residents.size());
        assertEquals("John", residents.get(0).getFirstName());
        assertEquals(savedUnit, residents.get(0).getUnit());
    }

    @Test
    void testFindByUnitIdAndIsActive() {
        // Create property and unit
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setProperty(savedProperty);
        Unit savedUnit = entityManager.persistAndFlush(unit);

        // Create active resident
        Resident activeResident = new Resident();
        activeResident.setFirstName("John");
        activeResident.setLastName("Doe");
        activeResident.setUnit(savedUnit);
        activeResident.setProperty(savedProperty);
        activeResident.setMonthlyRent(new BigDecimal("1200.00"));
        activeResident.setMoveInDate(LocalDate.of(2024, 1, 1));
        activeResident.setIsActive(true);
        entityManager.persistAndFlush(activeResident);

        // Create inactive resident
        Resident inactiveResident = new Resident();
        inactiveResident.setFirstName("Jane");
        inactiveResident.setLastName("Smith");
        inactiveResident.setUnit(savedUnit);
        inactiveResident.setProperty(savedProperty);
        inactiveResident.setMonthlyRent(new BigDecimal("1200.00"));
        inactiveResident.setMoveInDate(LocalDate.of(2024, 1, 1));
        inactiveResident.setIsActive(false);
        entityManager.persistAndFlush(inactiveResident);

        // Find active resident
        Optional<Resident> foundActive = residentRepository.findByUnitIdAndIsActive(savedUnit.getId(), true);
        assertTrue(foundActive.isPresent());
        assertEquals("John", foundActive.get().getFirstName());

        // Find inactive resident
        Optional<Resident> foundInactive = residentRepository.findByUnitIdAndIsActive(savedUnit.getId(), false);
        assertTrue(foundInactive.isPresent());
        assertEquals("Jane", foundInactive.get().getFirstName());
    }

    @Test
    void testFindByIsActive() {
        // Create a property first
        Property property = new Property();
        property.setPropertyName("Test Property");
        property.setAddress("123 Test St");
        property.setCity("Test City");
        property.setState("TS");
        property.setZipCode("12345");
        entityManager.persistAndFlush(property);

        // Create active resident
        Resident activeResident = new Resident();
        activeResident.setFirstName("John");
        activeResident.setLastName("Doe");
        activeResident.setProperty(property);
        activeResident.setMonthlyRent(new BigDecimal("1200.00"));
        activeResident.setMoveInDate(LocalDate.of(2024, 1, 1));
        activeResident.setIsActive(true);
        entityManager.persistAndFlush(activeResident);

        // Create inactive resident
        Resident inactiveResident = new Resident();
        inactiveResident.setFirstName("Jane");
        inactiveResident.setLastName("Smith");
        inactiveResident.setProperty(property);
        inactiveResident.setMonthlyRent(new BigDecimal("1200.00"));
        inactiveResident.setMoveInDate(LocalDate.of(2024, 1, 1));
        inactiveResident.setIsActive(false);
        entityManager.persistAndFlush(inactiveResident);

        // Find all active residents
        List<Resident> activeResidents = residentRepository.findByIsActive(true);
        assertEquals(1, activeResidents.size());
        assertEquals("John", activeResidents.get(0).getFirstName());

        // Find all inactive residents
        List<Resident> inactiveResidents = residentRepository.findByIsActive(false);
        assertEquals(1, inactiveResidents.size());
        assertEquals("Jane", inactiveResidents.get(0).getFirstName());
    }
}