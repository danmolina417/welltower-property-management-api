package com.welltower.propertymanagement.model;

import com.welltower.propertymanagement.model.Resident;
import com.welltower.propertymanagement.model.Unit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class ResidentTest {

    @Test
    void testResidentCreation() {
        Resident resident = new Resident();
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setEmail("john.doe@example.com");
        resident.setPhoneNumber("555-1234");
        resident.setMonthlyRent(new BigDecimal("1200.00"));
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setIsActive(true);

        assertEquals("John", resident.getFirstName());
        assertEquals("Doe", resident.getLastName());
        assertEquals("john.doe@example.com", resident.getEmail());
        assertEquals("555-1234", resident.getPhoneNumber());
        assertEquals(new BigDecimal("1200.00"), resident.getMonthlyRent());
        assertEquals(LocalDate.of(2024, 1, 1), resident.getMoveInDate());
        assertTrue(resident.getIsActive());
    }

    @Test
    void testResidentWithUnit() {
        Unit unit = new Unit();
        unit.setUnitNumber("101");

        Resident resident = new Resident();
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setUnit(unit);

        assertEquals(unit, resident.getUnit());
        assertEquals("101", resident.getUnit().getUnitNumber());
    }

    @Test
    void testResidentMoveOut() {
        Resident resident = new Resident();
        resident.setFirstName("John");
        resident.setLastName("Doe");
        resident.setMoveInDate(LocalDate.of(2024, 1, 1));
        resident.setMoveOutDate(LocalDate.of(2024, 12, 31));
        resident.setIsActive(false);

        assertEquals(LocalDate.of(2024, 1, 1), resident.getMoveInDate());
        assertEquals(LocalDate.of(2024, 12, 31), resident.getMoveOutDate());
        assertFalse(resident.getIsActive());
    }

    @Test
    void testResidentEqualsAndHashCode() {
        Resident resident1 = new Resident();
        resident1.setId(1L);
        resident1.setFirstName("John");
        resident1.setLastName("Doe");

        Resident resident2 = new Resident();
        resident2.setId(1L);
        resident2.setFirstName("John");
        resident2.setLastName("Doe");

        Resident resident3 = new Resident();
        resident3.setId(2L);
        resident3.setFirstName("Jane");
        resident3.setLastName("Smith");

        assertEquals(resident1, resident2);
        assertEquals(resident1.hashCode(), resident2.hashCode());
        assertNotEquals(resident1, resident3);
    }
}