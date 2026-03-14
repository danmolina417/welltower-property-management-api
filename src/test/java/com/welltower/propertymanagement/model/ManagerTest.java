package com.welltower.propertymanagement.model;

import com.welltower.propertymanagement.model.Manager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @Test
    void testManagerCreation() {
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@example.com");
        manager.setPhoneNumber("555-1234");

        assertEquals("John", manager.getFirstName());
        assertEquals("Doe", manager.getLastName());
        assertEquals("john.doe@example.com", manager.getEmail());
        assertEquals("555-1234", manager.getPhoneNumber());
    }

    @Test
    void testManagerEqualsAndHashCode() {
        Manager manager1 = new Manager();
        manager1.setId(1L);
        manager1.setFirstName("John");
        manager1.setLastName("Doe");
        manager1.setEmail("john.doe@example.com");

        Manager manager2 = new Manager();
        manager2.setId(1L);
        manager2.setFirstName("John");
        manager2.setLastName("Doe");
        manager2.setEmail("john.doe@example.com");

        Manager manager3 = new Manager();
        manager3.setId(2L);
        manager3.setFirstName("Jane");
        manager3.setLastName("Smith");
        manager3.setEmail("jane.smith@example.com");

        assertEquals(manager1, manager2);
        assertEquals(manager1.hashCode(), manager2.hashCode());
        assertNotEquals(manager1, manager3);
    }

    @Test
    void testManagerWithDifferentEmails() {
        Manager manager1 = new Manager();
        manager1.setEmail("john@example.com");

        Manager manager2 = new Manager();
        manager2.setEmail("john@example.com");

        // Same email should be considered equal for business logic purposes
        assertEquals(manager1.getEmail(), manager2.getEmail());
    }
}