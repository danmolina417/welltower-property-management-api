package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.ResidentDTO;
import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.model.Resident;
import com.welltower.propertymanagement.model.Unit;
import com.welltower.propertymanagement.repository.PropertyRepository;
import com.welltower.propertymanagement.repository.ResidentRepository;
import com.welltower.propertymanagement.repository.UnitRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResidentService {
    private final ResidentRepository residentRepository;
    private final PropertyRepository propertyRepository;
    private final UnitRepository unitRepository;
    private final UnitService unitService;

    public ResidentService(ResidentRepository residentRepository,
                           PropertyRepository propertyRepository,
                           UnitRepository unitRepository,
                           UnitService unitService) {
        this.residentRepository = residentRepository;
        this.propertyRepository = propertyRepository;
        this.unitRepository = unitRepository;
        this.unitService = unitService;
    }

    public ResidentDTO moveInResident(ResidentDTO residentDTO) {
        Property property = propertyRepository.findById(residentDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Unit unit = unitRepository.findById(residentDTO.getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        if (!unit.getProperty().getId().equals(residentDTO.getPropertyId())) {
            throw new RuntimeException("Unit does not belong to this property");
        }

        // Check if unit is already occupied
        if (unit.getIsOccupied()) {
            throw new RuntimeException("Unit is already occupied");
        }

        Resident resident = new Resident();
        resident.setProperty(property);
        resident.setUnit(unit);
        resident.setFirstName(residentDTO.getFirstName());
        resident.setLastName(residentDTO.getLastName());
        resident.setEmail(residentDTO.getEmail());
        resident.setPhoneNumber(residentDTO.getPhoneNumber());
        resident.setMonthlyRent(residentDTO.getMonthlyRent());
        resident.setMoveInDate(residentDTO.getMoveInDate() != null ? residentDTO.getMoveInDate() : LocalDate.now());
        resident.setIsActive(true);

        Resident savedResident = residentRepository.save(resident);
        unitService.updateOccupancyStatus(unit.getId(), true);

        return convertToDTO(savedResident);
    }

    public ResidentDTO getResident(Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + residentId));
        return convertToDTO(resident);
    }

    public List<ResidentDTO> getResidentsByProperty(Long propertyId) {
        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        return residentRepository.findByPropertyIdAndIsActive(propertyId, true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResidentDTO> getResidentsByUnit(Long unitId) {
        unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));

        return residentRepository.findByUnitId(unitId).stream()
                .filter(r -> Boolean.TRUE.equals(r.getIsActive()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResidentDTO updateRent(Long residentId, BigDecimal newRent) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + residentId));

        resident.setMonthlyRent(newRent);
        Resident updatedResident = residentRepository.save(resident);
        return convertToDTO(updatedResident);
    }

    public void moveOutResident(Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + residentId));

        resident.setMoveOutDate(LocalDate.now());
        resident.setIsActive(false);

        // Mark unit as unoccupied
        if (resident.getUnit() != null) {
            unitService.updateOccupancyStatus(resident.getUnit().getId(), false);
        }

        residentRepository.save(resident);
    }

    public void moveOutResidentOnDate(Long residentId, LocalDate moveOutDate) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + residentId));

        resident.setMoveOutDate(moveOutDate);
        resident.setIsActive(false);

        // Mark unit as unoccupied
        if (resident.getUnit() != null) {
            unitService.updateOccupancyStatus(resident.getUnit().getId(), false);
        }

        residentRepository.save(resident);
    }

    private ResidentDTO convertToDTO(Resident resident) {
        ResidentDTO dto = new ResidentDTO();
        dto.setId(resident.getId());
        dto.setPropertyId(resident.getProperty().getId());
        dto.setUnitId(resident.getUnit() != null ? resident.getUnit().getId() : null);
        dto.setFirstName(resident.getFirstName());
        dto.setLastName(resident.getLastName());
        dto.setEmail(resident.getEmail());
        dto.setPhoneNumber(resident.getPhoneNumber());
        dto.setMonthlyRent(resident.getMonthlyRent());
        dto.setMoveInDate(resident.getMoveInDate());
        dto.setMoveOutDate(resident.getMoveOutDate());
        dto.setIsActive(resident.getIsActive());
        return dto;
    }
}
