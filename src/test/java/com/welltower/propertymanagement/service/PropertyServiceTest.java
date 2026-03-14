package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.PropertyDTO;
import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Manager;
import com.welltower.propertymanagement.repository.PropertyRepository;
import com.welltower.propertymanagement.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private PropertyService propertyService;

    @Test
    void testGetAllProperties() {
        // Arrange
        PropertyDTO propertyDTO1 = new PropertyDTO();
        propertyDTO1.setId(1L);
        propertyDTO1.setPropertyName("Property 1");

        PropertyDTO propertyDTO2 = new PropertyDTO();
        propertyDTO2.setId(2L);
        propertyDTO2.setPropertyName("Property 2");

        when(propertyRepository.findByIsActive(true)).thenReturn(Arrays.asList(
            createProperty(1L, "Property 1"),
            createProperty(2L, "Property 2")
        ));

        // Act
        List<PropertyDTO> properties = propertyService.getAllProperties();

        // Assert
        assertEquals(2, properties.size());
        assertEquals("Property 1", properties.get(0).getPropertyName());
        assertEquals("Property 2", properties.get(1).getPropertyName());
        verify(propertyRepository, times(1)).findByIsActive(true);
    }

    @Test
    void testGetPropertyById() {
        // Arrange
        Property property = createProperty(1L, "Test Property");
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Act
        PropertyDTO foundProperty = propertyService.getProperty(1L);

        // Assert
        assertEquals(1L, foundProperty.getId());
        assertEquals("Test Property", foundProperty.getPropertyName());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPropertyByIdNotFound() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propertyService.getProperty(1L);
        });
        assertEquals("Property not found with id: 1", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProperty() {
        // Arrange
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPropertyName("New Property");
        propertyDTO.setAddress("123 New St");

        Property savedProperty = new Property();
        savedProperty.setId(1L);
        savedProperty.setPropertyName("New Property");
        savedProperty.setAddress("123 New St");
        savedProperty.setCity("Test City");
        savedProperty.setState("TS");
        savedProperty.setZipCode("12345");
        savedProperty.setIsActive(true);

        when(propertyRepository.save(any(Property.class))).thenReturn(savedProperty);

        // Act
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO);

        // Assert
        assertEquals("New Property", createdProperty.getPropertyName());
        assertEquals("123 New St", createdProperty.getAddress());
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testUpdateProperty() {
        // Arrange
        Property existingProperty = createProperty(1L, "Old Name");
        PropertyDTO updatedPropertyDTO = new PropertyDTO();
        updatedPropertyDTO.setPropertyName("New Name");
        updatedPropertyDTO.setAddress("456 Updated St");

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(existingProperty);

        // Act
        PropertyDTO result = propertyService.updateProperty(1L, updatedPropertyDTO);

        // Assert
        assertEquals("New Name", result.getPropertyName());
        assertEquals("456 Updated St", result.getAddress());
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(existingProperty);
    }

    @Test
    void testUpdatePropertyNotFound() {
        // Arrange
        PropertyDTO updatedPropertyDTO = new PropertyDTO();
        updatedPropertyDTO.setPropertyName("New Name");

        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propertyService.updateProperty(1L, updatedPropertyDTO);
        });
        assertEquals("Property not found with id: 1", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testDeleteProperty() {
        // Arrange
        Property property = createProperty(1L, "Test Property");

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Act
        propertyService.deleteProperty(1L);

        // Assert
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(property);
        assertFalse(property.getIsActive());
    }

    @Test
    void testDeletePropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propertyService.deleteProperty(1L);
        });
        assertEquals("Property not found with id: 1", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testAssignManagerToProperty() {
        // Arrange
        Property property = createProperty(1L, "Test Property");
        Manager manager = new Manager();
        manager.setId(1L);
        manager.setFirstName("John");
        manager.setLastName("Doe");

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        // Act
        PropertyDTO result = propertyService.assignManagerToProperty(1L, 1L);

        // Assert
        assertEquals(1L, result.getManagerId());
        assertEquals("John Doe", result.getManagerName());
        verify(propertyRepository, times(1)).findById(1L);
        verify(managerRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testAssignManagerToPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propertyService.assignManagerToProperty(1L, 1L);
        });
        assertEquals("Property not found with id: 1", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1L);
        verify(managerRepository, never()).findById(anyLong());
    }

    @Test
    void testAssignManagerToPropertyManagerNotFound() {
        // Arrange
        Property property = new Property();
        property.setId(1L);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(managerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propertyService.assignManagerToProperty(1L, 1L);
        });
        assertEquals("Manager not found with id: 1", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1L);
        verify(managerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPropertiesByManager() {
        // Arrange
        Manager manager = new Manager();
        manager.setId(1L);
        manager.setFirstName("John");
        manager.setLastName("Doe");

        Property property1 = createProperty(1L, "Property 1");
        property1.setManager(manager);
        Property property2 = createProperty(2L, "Property 2");
        property2.setManager(manager);

        when(propertyRepository.findByManagerIdAndIsActive(1L, true)).thenReturn(Arrays.asList(property1, property2));

        // Act
        List<PropertyDTO> properties = propertyService.getPropertiesByManager(1L);

        // Assert
        assertEquals(2, properties.size());
        verify(propertyRepository, times(1)).findByManagerIdAndIsActive(1L, true);
    }

    @Test
    void testGetPropertiesByManagerNotFound() {
        // Arrange
        when(propertyRepository.findByManagerIdAndIsActive(1L, true)).thenReturn(Arrays.asList());

        // Act
        List<PropertyDTO> properties = propertyService.getPropertiesByManager(1L);

        // Assert
        assertEquals(0, properties.size());
        verify(propertyRepository, times(1)).findByManagerIdAndIsActive(1L, true);
    }

    private Property createProperty(Long id, String name) {
        Property property = new Property();
        property.setId(id);
        property.setPropertyName(name);
        property.setAddress("123 Test St");
        property.setCity("Test City");
        property.setState("TS");
        property.setZipCode("12345");
        return property;
    }
}