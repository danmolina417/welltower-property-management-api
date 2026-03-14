package com.welltower.propertymanagement.model;

import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Manager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class PropertyTest {

    @Test
    void testPropertyCreation() {
        Property property = new Property();
        property.setPropertyName("Test Property");
        property.setAddress("123 Test St");
        property.setCity("Test City");
        property.setState("TS");
        property.setZipCode("12345");

        assertEquals("Test Property", property.getPropertyName());
        assertEquals("123 Test St", property.getAddress());
        assertEquals("Test City", property.getCity());
        assertEquals("TS", property.getState());
        assertEquals("12345", property.getZipCode());
    }

    @Test
    void testPropertyWithManager() {
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john@example.com");

        Property property = new Property();
        property.setPropertyName("Test Property");
        property.setManager(manager);

        assertEquals(manager, property.getManager());
        assertEquals("John", property.getManager().getFirstName());
        assertEquals("Doe", property.getManager().getLastName());
    }

    @Test
    void testPropertyEqualsAndHashCode() {
        Property property1 = new Property();
        property1.setId(1L);
        property1.setPropertyName("Test Property");

        Property property2 = new Property();
        property2.setId(1L);
        property2.setPropertyName("Test Property");

        Property property3 = new Property();
        property3.setId(2L);
        property3.setPropertyName("Different Property");

        assertEquals(property1, property2);
        assertEquals(property1.hashCode(), property2.hashCode());
        assertNotEquals(property1, property3);
    }
}