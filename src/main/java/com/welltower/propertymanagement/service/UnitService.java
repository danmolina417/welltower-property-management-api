package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.UnitDTO;
import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.repository.PropertyRepository;
import com.welltower.propertymanagement.repository.UnitRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UnitService {
    private final UnitRepository unitRepository;
    private final PropertyRepository propertyRepository;

    public UnitService(UnitRepository unitRepository, PropertyRepository propertyRepository) {
        this.unitRepository = unitRepository;
        this.propertyRepository = propertyRepository;
    }

    public UnitDTO createUnit(Long propertyId, UnitDTO unitDTO) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        // Check if unit number already exists for this property
        unitRepository.findByPropertyIdAndUnitNumber(propertyId, unitDTO.getUnitNumber())
                .ifPresent(u -> {
                    throw new RuntimeException("Unit number already exists for this property");
                });

        Unit unit = new Unit();
        unit.setProperty(property);
        unit.setUnitNumber(unitDTO.getUnitNumber());
        unit.setBedrooms(unitDTO.getBedrooms());
        unit.setBathrooms(unitDTO.getBathrooms());
        unit.setSquareFeet(unitDTO.getSquareFeet());
        unit.setIsActive(true);
        unit.setIsOccupied(false);

        Unit savedUnit = unitRepository.save(unit);
        return convertToDTO(savedUnit);
    }

    public UnitDTO getUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));
        return convertToDTO(unit);
    }

    public List<UnitDTO> getUnitsByProperty(Long propertyId) {
        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        return unitRepository.findByPropertyIdOrderByUnitNumber(propertyId).stream()
                .filter(u -> Boolean.TRUE.equals(u.getIsActive()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UnitDTO updateUnit(Long unitId, UnitDTO unitDTO) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));

        if (unitDTO.getBedrooms() != null) {
            unit.setBedrooms(unitDTO.getBedrooms());
        }
        if (unitDTO.getBathrooms() != null) {
            unit.setBathrooms(unitDTO.getBathrooms());
        }
        if (unitDTO.getSquareFeet() != null) {
            unit.setSquareFeet(unitDTO.getSquareFeet());
        }

        Unit updatedUnit = unitRepository.save(unit);
        return convertToDTO(updatedUnit);
    }

    public void deactivateUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));
        unit.setIsActive(false);
        unit.setIsOccupied(false);
        unitRepository.save(unit);
    }

    public void reactivateUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));
        unit.setIsActive(true);
        unitRepository.save(unit);
    }

    protected void updateOccupancyStatus(Long unitId, Boolean isOccupied) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));
        unit.setIsOccupied(isOccupied);
        unitRepository.save(unit);
    }

    private UnitDTO convertToDTO(Unit unit) {
        UnitDTO dto = new UnitDTO();
        dto.setId(unit.getId());
        dto.setPropertyId(unit.getProperty().getId());
        dto.setUnitNumber(unit.getUnitNumber());
        dto.setBedrooms(unit.getBedrooms());
        dto.setBathrooms(unit.getBathrooms());
        dto.setSquareFeet(unit.getSquareFeet());
        dto.setIsActive(unit.getIsActive());
        dto.setIsOccupied(unit.getIsOccupied());
        return dto;
    }
}
