package com.welltower.propertymanagement.model;

import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.model.Property;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class UnitTest {

    @Test
    void testUnitCreation() {
        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setBedrooms("2");
        unit.setBathrooms("1");
        unit.setSquareFeet(800.0);
        unit.setIsOccupied(false);

        assertEquals("101", unit.getUnitNumber());
        assertEquals("2", unit.getBedrooms());
        assertEquals("1", unit.getBathrooms());
        assertEquals(800.0, unit.getSquareFeet());
        assertFalse(unit.getIsOccupied());
    }

    @Test
    void testUnitWithProperty() {
        Property property = new Property();
        property.setPropertyName("Test Property");

        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setProperty(property);

        assertEquals(property, unit.getProperty());
        assertEquals("Test Property", unit.getProperty().getPropertyName());
    }

    @Test
    void testUnitOccupancy() {
        Unit unit = new Unit();
        unit.setUnitNumber("101");
        unit.setIsOccupied(false);

        assertFalse(unit.getIsOccupied());

        unit.setIsOccupied(true);
        assertTrue(unit.getIsOccupied());
    }

    @Test
    void testUnitEqualsAndHashCode() {
        Unit unit1 = new Unit();
        unit1.setId(1L);
        unit1.setUnitNumber("101");

        Unit unit2 = new Unit();
        unit2.setId(1L);
        unit2.setUnitNumber("101");

        Unit unit3 = new Unit();
        unit3.setId(2L);
        unit3.setUnitNumber("102");

        assertEquals(unit1, unit2);
        assertEquals(unit1.hashCode(), unit2.hashCode());
        assertNotEquals(unit1, unit3);
    }
}