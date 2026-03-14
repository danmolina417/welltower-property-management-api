package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Manager;
import com.welltower.propertymanagement.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@Disabled
@WebMvcTest(PropertyController.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProperties() throws Exception {
        // Arrange
        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("Property 1");
        property1.setAddress("123 Main St");

        Property property2 = new Property();
        property2.setId(2L);
        property2.setName("Property 2");
        property2.setAddress("456 Oak St");

        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property1, property2));

        // Act & Assert
        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].property_id").value(1))
                .andExpect(jsonPath("$[0].property_name").value("Property 1"))
                .andExpect(jsonPath("$[1].property_id").value(2))
                .andExpect(jsonPath("$[1].property_name").value("Property 2"));

        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    void testGetPropertyById() throws Exception {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        property.setName("Test Property");
        property.setAddress("123 Test St");

        when(propertyService.getPropertyById(1L)).thenReturn(property);

        // Act & Assert
        mockMvc.perform(get("/properties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("Test Property"))
                .andExpect(jsonPath("$.address").value("123 Test St"));

        verify(propertyService, times(1)).getPropertyById(1L);
    }

    @Test
    void testCreateProperty() throws Exception {
        // Arrange
        Property property = new Property();
        property.setName("New Property");
        property.setAddress("123 New St");
        property.setCity("New City");
        property.setState("NS");
        property.setZipCode("12345");

        Property savedProperty = new Property();
        savedProperty.setId(1L);
        savedProperty.setName("New Property");
        savedProperty.setAddress("123 New St");
        savedProperty.setCity("New City");
        savedProperty.setState("NS");
        savedProperty.setZipCode("12345");

        when(propertyService.createProperty(any(Property.class))).thenReturn(savedProperty);

        // Act & Assert
        mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(property)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("New Property"));

        verify(propertyService, times(1)).createProperty(any(Property.class));
    }

    @Test
    void testUpdateProperty() throws Exception {
        // Arrange
        Property updatedProperty = new Property();
        updatedProperty.setName("Updated Property");
        updatedProperty.setAddress("456 Updated St");

        Property resultProperty = new Property();
        resultProperty.setId(1L);
        resultProperty.setName("Updated Property");
        resultProperty.setAddress("456 Updated St");

        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenReturn(resultProperty);

        // Act & Assert
        mockMvc.perform(put("/properties/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProperty)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("Updated Property"));

        verify(propertyService, times(1)).updateProperty(eq(1L), any(Property.class));
    }

    @Test
    void testDeleteProperty() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/properties/1"))
                .andExpect(status().isNoContent());

        verify(propertyService, times(1)).deleteProperty(1L);
    }

    @Test
    void testAssignManagerToProperty() throws Exception {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        property.setName("Test Property");

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setName("John Doe");

        property.setManager(manager);

        when(propertyService.assignManagerToProperty(1L, 1L)).thenReturn(property);

        // Act & Assert
        mockMvc.perform(put("/properties/1/manager/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("Test Property"))
                .andExpect(jsonPath("$.manager_id").value(1))
                .andExpect(jsonPath("$.manager_name").value("John Doe"));

        verify(propertyService, times(1)).assignManagerToProperty(1L, 1L);
    }

    @Test
    void testGetPropertiesByManager() throws Exception {
        // Arrange
        Manager manager = new Manager();
        manager.setId(1L);
        manager.setName("John Doe");

        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("Property 1");
        property1.setManager(manager);

        Property property2 = new Property();
        property2.setId(2L);
        property2.setName("Property 2");
        property2.setManager(manager);

        when(propertyService.getPropertiesByManager(1L)).thenReturn(Arrays.asList(property1, property2));

        // Act & Assert
        mockMvc.perform(get("/properties/manager/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].property_id").value(1))
                .andExpect(jsonPath("$[0].property_name").value("Property 1"))
                .andExpect(jsonPath("$[1].property_id").value(2))
                .andExpect(jsonPath("$[1].property_name").value("Property 2"));

        verify(propertyService, times(1)).getPropertiesByManager(1L);
    }
}