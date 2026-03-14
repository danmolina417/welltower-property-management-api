package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class UnitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UnitRepository unitRepository;

    @Test
    void testSaveAndFindUnit() {
        // Create property first
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        // Create and save a unit
        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setBedrooms("2");
        unit.setBathrooms("1");
        unit.setSquareFeet(800.0);
        unit.setIsOccupied(false);
        unit.setProperty(savedProperty);

        Unit savedUnit = unitRepository.save(unit);
        entityManager.flush();

        // Find the unit
        Optional<Unit> foundUnit = unitRepository.findById(savedUnit.getId());

        assertTrue(foundUnit.isPresent());
        assertEquals("101", foundUnit.get().getUnitNumber());
        assertEquals("2", foundUnit.get().getBedrooms());
        assertEquals(savedProperty, foundUnit.get().getProperty());
    }

    @Test
    void testFindByPropertyId() {
        // Create property
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        // Create units for the property
        Unit unit1 = new Unit();
        unit1.setUnitNumber("101");
        unit1.setProperty(savedProperty);

        Unit unit2 = new Unit();
        unit2.setUnitNumber("102");
        unit2.setProperty(savedProperty);

        entityManager.persistAndFlush(unit1);
        entityManager.persistAndFlush(unit2);

        // Find units by property ID
        List<Unit> units = unitRepository.findByPropertyIdOrderByUnitNumber(savedProperty.getId());

        assertEquals(2, units.size());
        assertTrue(units.stream().anyMatch(u -> u.getUnitNumber().equals("101")));
        assertTrue(units.stream().anyMatch(u -> u.getUnitNumber().equals("102")));
    }

    @Test
    void testFindByPropertyIdAndIsOccupied() {
        // Create property
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        // Create occupied and vacant units
        Unit occupiedUnit = new Unit();
        occupiedUnit.setUnitNumber("101");
        occupiedUnit.setIsOccupied(true);
        occupiedUnit.setProperty(savedProperty);

        Unit vacantUnit = new Unit();
        vacantUnit.setUnitNumber("102");
        vacantUnit.setIsOccupied(false);
        vacantUnit.setProperty(savedProperty);

        entityManager.persistAndFlush(occupiedUnit);
        entityManager.persistAndFlush(vacantUnit);

        // Find occupied units
        List<Unit> occupiedUnits = unitRepository.findByPropertyIdAndIsOccupied(savedProperty.getId(), true);
        assertEquals(1, occupiedUnits.size());
        assertEquals("101", occupiedUnits.get(0).getUnitNumber());

        // Find vacant units
        List<Unit> vacantUnits = unitRepository.findByPropertyIdAndIsOccupied(savedProperty.getId(), false);
        assertEquals(1, vacantUnits.size());
        assertEquals("102", vacantUnits.get(0).getUnitNumber());
    }

    @Test
    void testFindByPropertyIdAndUnitNumber() {
        // Create property
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        // Create unit
        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setProperty(savedProperty);
        entityManager.persistAndFlush(unit);

        // Find unit by property ID and unit number
        Optional<Unit> foundUnit = unitRepository.findByPropertyIdAndUnitNumber(savedProperty.getId(), "101");

        assertTrue(foundUnit.isPresent());
        assertEquals("101", foundUnit.get().getUnitNumber());
        assertEquals(savedProperty, foundUnit.get().getProperty());
    }
}