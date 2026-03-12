package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.UnitDTO;
import com.welltower.propertymanagement.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @PostMapping("/property/{propertyId}")
    public ResponseEntity<UnitDTO> createUnit(
            @PathVariable Long propertyId,
            @RequestBody UnitDTO unitDTO) {
        UnitDTO createdUnit = unitService.createUnit(propertyId, unitDTO);
        return new ResponseEntity<>(createdUnit, HttpStatus.CREATED);
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<UnitDTO> getUnit(@PathVariable Long unitId) {
        UnitDTO unit = unitService.getUnit(unitId);
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<UnitDTO>> getUnitsByProperty(@PathVariable Long propertyId) {
        List<UnitDTO> units = unitService.getUnitsByProperty(propertyId);
        return new ResponseEntity<>(units, HttpStatus.OK);
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<UnitDTO> updateUnit(
            @PathVariable Long unitId,
            @RequestBody UnitDTO unitDTO) {
        UnitDTO updatedUnit = unitService.updateUnit(unitId, unitDTO);
        return new ResponseEntity<>(updatedUnit, HttpStatus.OK);
    }

    @PutMapping("/{unitId}/deactivate")
    public ResponseEntity<Void> deactivateUnit(@PathVariable Long unitId) {
        unitService.deactivateUnit(unitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{unitId}/reactivate")
    public ResponseEntity<Void> reactivateUnit(@PathVariable Long unitId) {
        unitService.reactivateUnit(unitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
