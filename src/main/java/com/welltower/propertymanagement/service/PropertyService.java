package com.welltower.propertymanagement.service;

import com.welltower.propertymanagement.dto.PropertyDTO;
import com.welltower.propertymanagement.model.Property;
import com.welltower.propertymanagement.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = Property.builder()
                .propertyName(propertyDTO.getPropertyName())
                .address(propertyDTO.getAddress())
                .city(propertyDTO.getCity())
                .state(propertyDTO.getState())
                .zipCode(propertyDTO.getZipCode())
                .isActive(true)
                .build();

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
        return PropertyDTO.builder()
                .propertyId(property.getPropertyId())
                .propertyName(property.getPropertyName())
                .address(property.getAddress())
                .city(property.getCity())
                .state(property.getState())
                .zipCode(property.getZipCode())
                .isActive(property.getIsActive())
                .build();
    }
}
