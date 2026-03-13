package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.RentRollDTO;
import com.welltower.propertymanagement.service.RentRollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports/rent-roll")
public class RentRollController {
    private final RentRollService rentRollService;

    public RentRollController(RentRollService rentRollService) {
        this.rentRollService = rentRollService;
    }

    @GetMapping("/property/{propertyId}/date/{date}")
    public ResponseEntity<List<RentRollDTO>> getRentRoll(
            @PathVariable Long propertyId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<RentRollDTO> rentRoll = rentRollService.generateRentRoll(propertyId, localDate);
        return new ResponseEntity<>(rentRoll, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}/range")
    public ResponseEntity<List<RentRollDTO>> getRentRollForDateRange(
            @PathVariable Long propertyId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<RentRollDTO> rentRoll = rentRollService.generateRentRollForDateRange(propertyId, start, end);
        return new ResponseEntity<>(rentRoll, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}/date/{date}/summary")
    public ResponseEntity<Map<String, Object>> getRentRollSummary(
            @PathVariable Long propertyId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        Map<String, Object> summary = rentRollService.generateRentRollSummary(propertyId, localDate);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}/range/summary")
    public ResponseEntity<List<Map<String, Object>>> getRentRollSummariesForDateRange(
            @PathVariable Long propertyId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Map<String, Object>> summaries = rentRollService.generateRentRollSummariesForDateRange(propertyId, start, end);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }
}
