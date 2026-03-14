package com.welltower.propertymanagement.repository;

import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class PropertyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    void testSaveAndFindProperty() {
        // Create and save a property
        Property property = new Property();
        property.setPropertyName("Test Property");
        property.setAddress("123 Test St");
        property.setCity("Test City");
        property.setState("TS");
        property.setZipCode("12345");

        Property savedProperty = propertyRepository.save(property);
        entityManager.flush();

        // Find the property
        Optional<Property> foundProperty = propertyRepository.findById(savedProperty.getId());

        assertTrue(foundProperty.isPresent());
        assertEquals("Test Property", foundProperty.get().getPropertyName());
        assertEquals("123 Test St", foundProperty.get().getAddress());
    }

    @Test
    void testFindByManagerId() {
        // Create manager
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john@example.com");
        Manager savedManager = entityManager.persistAndFlush(manager);

        // Create properties for the manager
        Property property1 = new Property();
        property1.setPropertyName("Property 1");
        property1.setManager(savedManager);

        Property property2 = new Property();
        property2.setPropertyName("Property 2");
        property2.setManager(savedManager);

        entityManager.persistAndFlush(property1);
        entityManager.persistAndFlush(property2);

        // Find properties by manager ID
        List<Property> properties = propertyRepository.findByManagerId(savedManager.getId());

        assertEquals(2, properties.size());
        assertTrue(properties.stream().anyMatch(p -> p.getPropertyName().equals("Property 1")));
        assertTrue(properties.stream().anyMatch(p -> p.getPropertyName().equals("Property 2")));
    }

    @Test
    void testFindAll() {
        // Create and save multiple properties
        Property property1 = new Property();
        property1.setPropertyName("Property 1");
        property1.setAddress("123 Main St");

        Property property2 = new Property();
        property2.setPropertyName("Property 2");
        property2.setAddress("456 Oak St");

        entityManager.persistAndFlush(property1);
        entityManager.persistAndFlush(property2);

        List<Property> properties = propertyRepository.findAll();

        assertTrue(properties.size() >= 2);
        assertTrue(properties.stream().anyMatch(p -> p.getPropertyName().equals("Property 1")));
        assertTrue(properties.stream().anyMatch(p -> p.getPropertyName().equals("Property 2")));
    }

    @Test
    void testDeleteProperty() {
        // Create and save a property
        Property property = new Property();
        property.setPropertyName("Test Property");
        Property savedProperty = entityManager.persistAndFlush(property);

        // Delete the property
        propertyRepository.deleteById(savedProperty.getId());

        // Verify it's deleted
        Optional<Property> deletedProperty = propertyRepository.findById(savedProperty.getId());
        assertFalse(deletedProperty.isPresent());
    }
}