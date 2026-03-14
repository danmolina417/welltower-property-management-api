package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.PropertyDTO;
import com.welltower.propertymanagement.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
    }

    @Test
    void testGetAllProperties() throws Exception {
        PropertyDTO property1 = new PropertyDTO();
        property1.setId(1L);
        property1.setPropertyName("Property 1");
        property1.setAddress("123 Main St");

        PropertyDTO property2 = new PropertyDTO();
        property2.setId(2L);
        property2.setPropertyName("Property 2");
        property2.setAddress("456 Oak St");

        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property1, property2));

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
        PropertyDTO property = new PropertyDTO();
        property.setId(1L);
        property.setPropertyName("Test Property");
        property.setAddress("123 Test St");

        when(propertyService.getProperty(1L)).thenReturn(property);

        mockMvc.perform(get("/properties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("Test Property"))
                .andExpect(jsonPath("$.address").value("123 Test St"));

        verify(propertyService, times(1)).getProperty(1L);
    }

    @Test
    void testCreateProperty() throws Exception {
        PropertyDTO property = new PropertyDTO();
        property.setPropertyName("New Property");
        property.setAddress("123 New St");
        property.setCity("New City");
        property.setState("NS");
        property.setZipCode("12345");

        PropertyDTO savedProperty = new PropertyDTO();
        savedProperty.setId(1L);
        savedProperty.setPropertyName("New Property");
        savedProperty.setAddress("123 New St");
        savedProperty.setCity("New City");
        savedProperty.setState("NS");
        savedProperty.setZipCode("12345");

        when(propertyService.createProperty(any(PropertyDTO.class))).thenReturn(savedProperty);

        mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(property)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("New Property"));

        verify(propertyService, times(1)).createProperty(any(PropertyDTO.class));
    }

    @Test
    void testUpdateProperty() throws Exception {
        PropertyDTO updatedProperty = new PropertyDTO();
        updatedProperty.setPropertyName("Updated Property");
        updatedProperty.setAddress("456 Updated St");

        PropertyDTO resultProperty = new PropertyDTO();
        resultProperty.setId(1L);
        resultProperty.setPropertyName("Updated Property");
        resultProperty.setAddress("456 Updated St");

        when(propertyService.updateProperty(eq(1L), any(PropertyDTO.class))).thenReturn(resultProperty);

        mockMvc.perform(put("/properties/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProperty)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.property_name").value("Updated Property"));

        verify(propertyService, times(1)).updateProperty(eq(1L), any(PropertyDTO.class));
    }

    @Test
    void testDeleteProperty() throws Exception {
        mockMvc.perform(delete("/properties/1"))
                .andExpect(status().isNoContent());

        verify(propertyService, times(1)).deleteProperty(1L);
    }

    @Test
    void testAssignManagerToProperty() throws Exception {
        PropertyDTO property = new PropertyDTO();
        property.setId(1L);
        property.setPropertyName("Test Property");
        property.setManagerId(1L);
        property.setManagerName("John Doe");

        when(propertyService.assignManagerToProperty(1L, 1L)).thenReturn(property);

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
        PropertyDTO property1 = new PropertyDTO();
        property1.setId(1L);
        property1.setPropertyName("Property 1");
        property1.setManagerId(1L);
        property1.setManagerName("John Doe");

        PropertyDTO property2 = new PropertyDTO();
        property2.setId(2L);
        property2.setPropertyName("Property 2");
        property2.setManagerId(1L);
        property2.setManagerName("John Doe");

        when(propertyService.getPropertiesByManager(1L)).thenReturn(Arrays.asList(property1, property2));

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
