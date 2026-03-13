package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.ResidentDTO;
import com.welltower.propertymanagement.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/residents")
public class ResidentController {
    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping("/move-in")
    public ResponseEntity<ResidentDTO> moveInResident(@RequestBody ResidentDTO residentDTO) {
        ResidentDTO createdResident = residentService.moveInResident(residentDTO);
        return new ResponseEntity<>(createdResident, HttpStatus.CREATED);
    }

    @GetMapping("/{residentId}")
    public ResponseEntity<ResidentDTO> getResident(@PathVariable Long residentId) {
        ResidentDTO resident = residentService.getResident(residentId);
        return new ResponseEntity<>(resident, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ResidentDTO>> getResidentsByProperty(@PathVariable Long propertyId) {
        List<ResidentDTO> residents = residentService.getResidentsByProperty(propertyId);
        return new ResponseEntity<>(residents, HttpStatus.OK);
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<ResidentDTO>> getResidentsByUnit(@PathVariable Long unitId) {
        List<ResidentDTO> residents = residentService.getResidentsByUnit(unitId);
        return new ResponseEntity<>(residents, HttpStatus.OK);
    }

    @PutMapping("/{residentId}/rent")
    public ResponseEntity<ResidentDTO> updateRent(
            @PathVariable Long residentId,
            @RequestParam BigDecimal newRent) {
        ResidentDTO updatedResident = residentService.updateRent(residentId, newRent);
        return new ResponseEntity<>(updatedResident, HttpStatus.OK);
    }

    @PutMapping("/{residentId}/move-out")
    public ResponseEntity<Void> moveOutResident(@PathVariable Long residentId) {
        residentService.moveOutResident(residentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{residentId}/move-out-on-date")
    public ResponseEntity<Void> moveOutResidentOnDate(
            @PathVariable Long residentId,
            @RequestParam String moveOutDate) {
        LocalDate date;
        try {
            // Try ISO format first (yyyy-MM-dd)
            date = LocalDate.parse(moveOutDate);
        } catch (Exception e) {
            // Try MM/dd/yyyy format
            try {
                String[] parts = moveOutDate.split("/");
                if (parts.length == 3) {
                    date = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                } else {
                    throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd or MM/dd/yyyy");
                }
            } catch (Exception e2) {
                throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd or MM/dd/yyyy");
            }
        }
        residentService.moveOutResidentOnDate(residentId, date);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
