package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
class ManagerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    void testSaveAndFindManager() {
        // Create and save a manager
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@example.com");
        manager.setPhoneNumber("555-1234");

        Manager savedManager = managerRepository.save(manager);
        entityManager.flush();

        // Find the manager
        Optional<Manager> foundManager = managerRepository.findById(savedManager.getId());

        assertTrue(foundManager.isPresent());
        assertEquals("John", foundManager.get().getFirstName());
        assertEquals("Doe", foundManager.get().getLastName());
        assertEquals("john.doe@example.com", foundManager.get().getEmail());
    }

    @Test
    void testFindByEmail() {
        // Create and save a manager
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@example.com");
        manager.setPhoneNumber("555-1234");
        entityManager.persistAndFlush(manager);

        // Find manager by email
        Optional<Manager> foundManager = managerRepository.findByEmail("john.doe@example.com");

        assertTrue(foundManager.isPresent());
        assertEquals("John", foundManager.get().getFirstName());
        assertEquals("Doe", foundManager.get().getLastName());
        assertEquals("john.doe@example.com", foundManager.get().getEmail());
    }

    @Test
    void testUniqueEmailConstraint() {
        // Create first manager
        Manager manager1 = new Manager();
        manager1.setFirstName("John");
        manager1.setLastName("Doe");
        manager1.setEmail("john.doe@example.com");
        manager1.setPhoneNumber("555-1234");
        managerRepository.save(manager1);
        entityManager.flush();

        // Try to create second manager with same email
        Manager manager2 = new Manager();
        manager2.setFirstName("Jane");
        manager2.setLastName("Smith");
        manager2.setEmail("john.doe@example.com");
        manager2.setPhoneNumber("555-5678");

        assertThrows(DataIntegrityViolationException.class, () -> {
            managerRepository.save(manager2);
            entityManager.flush();
        });
    }

    @Test
    void testFindByEmailNotFound() {
        // Try to find non-existent email
        Optional<Manager> foundManager = managerRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundManager.isPresent());
    }

    @Test
    void testDeleteManager() {
        // Create and save a manager
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@example.com");
        manager.setPhoneNumber("555-1234");
        Manager savedManager = entityManager.persistAndFlush(manager);

        // Delete the manager
        managerRepository.deleteById(savedManager.getId());

        // Verify it's deleted
        Optional<Manager> deletedManager = managerRepository.findById(savedManager.getId());
        assertFalse(deletedManager.isPresent());
    }
}