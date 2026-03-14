package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.PropertyDTO;
import com.welltower.propertymanagement.service.PropertyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

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
        PropertyDTO property = new PropertyDTO();
        property.setId(1L);
        property.setPropertyName("Test Property");

        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property));

        // Act & Assert
        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].property_id").value(1))
            .andExpect(jsonPath("$[0].property_name").value("Test Property"));

        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    void testGetPropertyById() throws Exception {
        // Arrange
        PropertyDTO property = new PropertyDTO();
        property.setId(1L);
        property.setPropertyName("Test Property");

        when(propertyService.getProperty(1L)).thenReturn(property);

        // Act & Assert
        mockMvc.perform(get("/properties/1"))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$.property_id").value(1))
            .andExpect(jsonPath("$.property_name").value("Test Property"));

        verify(propertyService, times(1)).getProperty(1L);
    }
}