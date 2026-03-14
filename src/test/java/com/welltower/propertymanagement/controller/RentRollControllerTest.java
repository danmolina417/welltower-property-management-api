package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.RentRollDTO;
import com.welltower.propertymanagement.service.RentRollService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RentRollControllerTest {

    private MockMvc mockMvc;

        private StubRentRollService rentRollService;

        @BeforeEach
        void setUp() {
                rentRollService = new StubRentRollService();
                RentRollController rentRollController = new RentRollController(rentRollService);
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

        rentRollService.generateRentRollResponse = Arrays.asList(rentRollDTO);

        // Act & Assert
        mockMvc.perform(get("/reports/rent-roll/property/1/date/2024-03-15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].unit_id").value(1))
                .andExpect(jsonPath("$[0].unit_number").value("101"))
                .andExpect(jsonPath("$[0].resident_id").value(1))
                .andExpect(jsonPath("$[0].resident_name").value("John Doe"))
                .andExpect(jsonPath("$[0].monthly_rent").value(1200.00));

        assertEquals(1L, rentRollService.lastPropertyIdForGenerateRentRoll);
        assertEquals(LocalDate.of(2024, 3, 15), rentRollService.lastDateForGenerateRentRoll);
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

        rentRollService.generateRentRollRangeResponse = Arrays.asList(rentRollDTO1, rentRollDTO2);

        // Act & Assert
        mockMvc.perform(get("/reports/rent-roll/property/1/range?startDate=2024-03-01&endDate=2024-03-02"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        assertEquals(1L, rentRollService.lastPropertyIdForGenerateRange);
        assertEquals(LocalDate.of(2024, 3, 1), rentRollService.lastStartDateForGenerateRange);
        assertEquals(LocalDate.of(2024, 3, 2), rentRollService.lastEndDateForGenerateRange);
    }

    @Test
    void testGetRentRollSummary() throws Exception {
        // Arrange
        Map<String, Object> summary = new HashMap<>();
        summary.put("date", "2024-03-15");
        summary.put("property_id", 1L);
        summary.put("total_units", 3);
        summary.put("occupied_units", 2);
        summary.put("vacant_units", 1);
        summary.put("occupancy_rate", "66.67%");
        summary.put("total_monthly_revenue", new BigDecimal("2500.00"));

        rentRollService.generateSummaryResponse = summary;

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

        assertEquals(1L, rentRollService.lastPropertyIdForGenerateSummary);
        assertEquals(LocalDate.of(2024, 3, 15), rentRollService.lastDateForGenerateSummary);
    }

    @Test
    void testGetRentRollSummariesForDateRange() throws Exception {
        // Arrange
        Map<String, Object> summary1 = new HashMap<>();
        summary1.put("date", "2024-03-01");
        summary1.put("property_id", 1L);
        summary1.put("total_units", 3);
        summary1.put("occupied_units", 2);
        summary1.put("vacant_units", 1);
        summary1.put("occupancy_rate", "66.67%");
        summary1.put("total_monthly_revenue", new BigDecimal("2500.00"));

        Map<String, Object> summary2 = new HashMap<>();
        summary2.put("date", "2024-03-02");
        summary2.put("property_id", 1L);
        summary2.put("total_units", 3);
        summary2.put("occupied_units", 2);
        summary2.put("vacant_units", 1);
        summary2.put("occupancy_rate", "66.67%");
        summary2.put("total_monthly_revenue", new BigDecimal("2500.00"));

        rentRollService.generateSummariesRangeResponse = Arrays.asList(summary1, summary2);

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

                assertEquals(1L, rentRollService.lastPropertyIdForGenerateSummariesRange);
                assertEquals(LocalDate.of(2024, 3, 1), rentRollService.lastStartDateForGenerateSummariesRange);
                assertEquals(LocalDate.of(2024, 3, 2), rentRollService.lastEndDateForGenerateSummariesRange);
        }

        private static class StubRentRollService extends RentRollService {
                private List<RentRollDTO> generateRentRollResponse = List.of();
                private List<RentRollDTO> generateRentRollRangeResponse = List.of();
                private Map<String, Object> generateSummaryResponse = Map.of();
                private List<Map<String, Object>> generateSummariesRangeResponse = List.of();

                private Long lastPropertyIdForGenerateRentRoll;
                private LocalDate lastDateForGenerateRentRoll;
                private Long lastPropertyIdForGenerateRange;
                private LocalDate lastStartDateForGenerateRange;
                private LocalDate lastEndDateForGenerateRange;
                private Long lastPropertyIdForGenerateSummary;
                private LocalDate lastDateForGenerateSummary;
                private Long lastPropertyIdForGenerateSummariesRange;
                private LocalDate lastStartDateForGenerateSummariesRange;
                private LocalDate lastEndDateForGenerateSummariesRange;

                StubRentRollService() {
                        super(null, null, null);
                }

                @Override
                public List<RentRollDTO> generateRentRoll(Long propertyId, LocalDate date) {
                        lastPropertyIdForGenerateRentRoll = propertyId;
                        lastDateForGenerateRentRoll = date;
                        return generateRentRollResponse;
                }

                @Override
                public List<RentRollDTO> generateRentRollForDateRange(Long propertyId, LocalDate startDate, LocalDate endDate) {
                        lastPropertyIdForGenerateRange = propertyId;
                        lastStartDateForGenerateRange = startDate;
                        lastEndDateForGenerateRange = endDate;
                        return generateRentRollRangeResponse;
                }

                @Override
                public Map<String, Object> generateRentRollSummary(Long propertyId, LocalDate date) {
                        lastPropertyIdForGenerateSummary = propertyId;
                        lastDateForGenerateSummary = date;
                        return generateSummaryResponse;
                }

                @Override
                public List<Map<String, Object>> generateRentRollSummariesForDateRange(Long propertyId, LocalDate startDate, LocalDate endDate) {
                        lastPropertyIdForGenerateSummariesRange = propertyId;
                        lastStartDateForGenerateSummariesRange = startDate;
                        lastEndDateForGenerateSummariesRange = endDate;
                        return generateSummariesRangeResponse;
                }
    }
}