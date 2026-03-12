package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PropertyDTO {
    @JsonProperty("property_id")
    private Long id;

    @JsonProperty("property_name")
    private String propertyName;

    private String address;
    private String city;
    private String state;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("is_active")
    private Boolean isActive;

    private List<UnitDTO> units;

    public PropertyDTO() {
    }

    public PropertyDTO(Long id, String propertyName, String address, String city, String state, String zipCode, Boolean isActive, List<UnitDTO> units) {
        this.id = id;
        this.propertyName = propertyName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.isActive = isActive;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<UnitDTO> getUnits() {
        return units;
    }

    public void setUnits(List<UnitDTO> units) {
        this.units = units;
    }
}
