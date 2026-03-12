package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.ResidentDTO;
import com.welltower.propertymanagement.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentController {
    private final ResidentService residentService;

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
            @RequestParam LocalDate moveOutDate) {
        residentService.moveOutResidentOnDate(residentId, moveOutDate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
