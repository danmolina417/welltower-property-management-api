package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Manager;
import com.welltower.propertymanagement.service.PropertyService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Test
    void testGetAllProperties() throws Exception {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        property.setName("Test Property");

        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property));

        // Act & Assert
        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Property"));

        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    void testGetPropertyById() throws Exception {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        property.setName("Test Property");

        when(propertyService.getPropertyById(1L)).thenReturn(property);

        // Act & Assert
        mockMvc.perform(get("/properties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Property"));

        verify(propertyService, times(1)).getPropertyById(1L);
    }
}