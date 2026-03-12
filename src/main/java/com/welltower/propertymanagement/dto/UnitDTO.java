package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitDTO {
    @JsonProperty("property_id")
    private Long propertyId;

    @JsonProperty("unit_id")
    private Long id;

    @JsonProperty("unit_number")
    private String unitNumber;

    private String bedrooms;
    private String bathrooms;

    @JsonProperty("square_feet")
    private Double squareFeet;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("is_occupied")
    private Boolean isOccupied;

    public UnitDTO() {
    }

    public UnitDTO(Long propertyId, Long id, String unitNumber, String bedrooms, String bathrooms, Double squareFeet, Boolean isActive, Boolean isOccupied) {
        this.propertyId = propertyId;
        this.id = id;
        this.unitNumber = unitNumber;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFeet = squareFeet;
        this.isActive = isActive;
        this.isOccupied = isOccupied;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(Double squareFeet) {
        this.squareFeet = squareFeet;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
