package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.RentRollDTO;
import com.welltower.propertymanagement.service.RentRollService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class RentRollControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RentRollService rentRollService;

    @InjectMocks
    private RentRollController rentRollController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rentRollController).build();
    }

    @Test
    void testGetRentRoll() throws Exception {
        // Arrange
        RentRollDTO rentRollDTO = new RentRollDTO();
        rentRollDTO.setUnitId(1L);
        rentRollDTO.setUnitNumber("101");
        rentRollDTO.setResidentId(1L);
        rentRollDTO.setResidentName("John Doe");
        rentRollDTO.setMonthlyRent(new BigDecimal("1200.00"));

        when(rentRollService.generateRentRoll(1L, LocalDate.of(2024, 3, 15)))
                .thenReturn(Arrays.asList(rentRollDTO));

        // Act & Assert
        mockMvc.perform(get("/reports/rent-roll/property/1/date/2024-03-15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].unitId").value(1))
                .andExpect(jsonPath("$[0].unitNumber").value("101"))
                .andExpect(jsonPath("$[0].residentId").value(1))
                .andExpect(jsonPath("$[0].residentName").value("John Doe"))
                .andExpect(jsonPath("$[0].monthlyRent").value(1200.00));

        verify(rentRollService, times(1)).generateRentRoll(1L, LocalDate.of(2024, 3, 15));
    }

    @Test
    void testGetRentRollForDateRange() throws Exception {
        // Arrange
        RentRollDTO rentRollDTO1 = new RentRollDTO();
        rentRollDTO1.setUnitId(1L);
        rentRollDTO1.setUnitNumber("101");
        rentRollDTO1.setResidentId(1L);
        rentRollDTO1.setResidentName("John Doe");
        rentRollDTO1.setMonthlyRent(new BigDecimal("1200.00"));

        RentRollDTO rentRollDTO2 = new RentRollDTO();
        rentRollDTO2.setUnitId(1L);
        rentRollDTO2.setUnitNumber("101");
        rentRollDTO2.setResidentId(1L);
        rentRollDTO2.setResidentName("John Doe");
        rentRollDTO2.setMonthlyRent(new BigDecimal("1200.00"));

        when(rentRollService.generateRentRollForDateRange(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 2)))
                .thenReturn(Arrays.asList(rentRollDTO1, rentRollDTO2));

        // Act & Assert
        mockMvc.perform(get("/reports/rent-roll/property/1/range?startDate=2024-03-01&endDate=2024-03-02"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(rentRollService, times(1)).generateRentRollForDateRange(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 2));
    }

    @Test
    void testGetRentRollSummary() throws Exception {
        // Arrange
        Map<String, Object> summary = new HashMap<>();
        summary.put("date", LocalDate.of(2024, 3, 15));
        summary.put("property_id", 1L);
        summary.put("total_units", 3);
        summary.put("occupied_units", 2);
        summary.put("vacant_units", 1);
        summary.put("occupancy_rate", "66.67%");
        summary.put("total_monthly_revenue", new BigDecimal("2500.00"));

        when(rentRollService.generateRentRollSummary(1L, LocalDate.of(2024, 3, 15)))
                .thenReturn(summary);

        // Act & Assert
        mockMvc.perform(get("/reports/rent-roll/property/1/date/2024-03-15/summary"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.date").value("2024-03-15"))
                .andExpect(jsonPath("$.property_id").value(1))
                .andExpect(jsonPath("$.total_units").value(3))
                .andExpect(jsonPath("$.occupied_units").value(2))
                .andExpect(jsonPath("$.vacant_units").value(1))
                .andExpect(jsonPath("$.occupancy_rate").value("66.67%"))
                .andExpect(jsonPath("$.total_monthly_revenue").value(2500.00));

        verify(rentRollService, times(1)).generateRentRollSummary(1L, LocalDate.of(2024, 3, 15));
    }

    @Test
    void testGetRentRollSummariesForDateRange() throws Exception {
        // Arrange
        Map<String, Object> summary1 = new HashMap<>();
        summary1.put("date", LocalDate.of(2024, 3, 1));
        summary1.put("property_id", 1L);
        summary1.put("total_units", 3);
        summary1.put("occupied_units", 2);
        summary1.put("vacant_units", 1);
        summary1.put("occupancy_rate", "66.67%");
        summary1.put("total_monthly_revenue", new BigDecimal("2500.00"));

        Map<String, Object> summary2 = new HashMap<>();
        summary2.put("date", LocalDate.of(2024, 3, 2));
        summary2.put("property_id", 1L);
        summary2.put("total_units", 3);
        summary2.put("occupied_units", 2);
        summary2.put("vacant_units", 1);
        summary2.put("occupancy_rate", "66.67%");
        summary2.put("total_monthly_revenue", new BigDecimal("2500.00"));

        when(rentRollService.generateRentRollSummariesForDateRange(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 2)))
                .thenReturn(Arrays.asList(summary1, summary2));

        // Act & Assert
        mockMvc.perform(get("/reports/rent-roll/property/1/range/summary?startDate=2024-03-01&endDate=2024-03-02"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].date").value("2024-03-01"))
                .andExpect(jsonPath("$[0].property_id").value(1))
                .andExpect(jsonPath("$[0].total_units").value(3))
                .andExpect(jsonPath("$[0].occupied_units").value(2))
                .andExpect(jsonPath("$[0].vacant_units").value(1))
                .andExpect(jsonPath("$[0].occupancy_rate").value("66.67%"))
                .andExpect(jsonPath("$[0].total_monthly_revenue").value(2500.00))
                .andExpect(jsonPath("$[1].date").value("2024-03-02"));

        verify(rentRollService, times(1)).generateRentRollSummariesForDateRange(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 2));
    }
}