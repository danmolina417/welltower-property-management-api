package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.PropertyDTO;
import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.repository.PropertyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setPropertyName(propertyDTO.getPropertyName());
        property.setAddress(propertyDTO.getAddress());
        property.setCity(propertyDTO.getCity());
        property.setState(propertyDTO.getState());
        property.setZipCode(propertyDTO.getZipCode());
        property.setIsActive(true);

        Property savedProperty = propertyRepository.save(property);
        return convertToDTO(savedProperty);
    }

    public PropertyDTO getProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        return convertToDTO(property);
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findByIsActive(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        if (propertyDTO.getPropertyName() != null) {
            property.setPropertyName(propertyDTO.getPropertyName());
        }
        if (propertyDTO.getAddress() != null) {
            property.setAddress(propertyDTO.getAddress());
        }
        if (propertyDTO.getCity() != null) {
            property.setCity(propertyDTO.getCity());
        }
        if (propertyDTO.getState() != null) {
            property.setState(propertyDTO.getState());
        }
        if (propertyDTO.getZipCode() != null) {
            property.setZipCode(propertyDTO.getZipCode());
        }

        Property updatedProperty = propertyRepository.save(property);
        return convertToDTO(updatedProperty);
    }

    public void deleteProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        property.setIsActive(false);
        propertyRepository.save(property);
    }

    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO dto = new PropertyDTO();
        dto.setId(property.getId());
        dto.setPropertyName(property.getPropertyName());
        dto.setAddress(property.getAddress());
        dto.setCity(property.getCity());
        dto.setState(property.getState());
        dto.setZipCode(property.getZipCode());
        dto.setIsActive(property.getIsActive());
        return dto;
    }
}
