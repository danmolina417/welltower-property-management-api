package com.welltower.propertymanagement.controller;

import com.welltower.propertymanagement.dto.PropertyDTO;
import com.welltower.propertymanagement.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable Long propertyId) {
        PropertyDTO property = propertyService.getProperty(propertyId);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable Long propertyId,
            @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updatedProperty = propertyService.updateProperty(propertyId, propertyDTO);
        return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
