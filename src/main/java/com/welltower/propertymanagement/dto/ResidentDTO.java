package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ResidentDTO {
    @JsonProperty("resident_id")
    private Long id;

    @JsonProperty("property_id")
    private Long propertyId;

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("monthly_rent")
    private BigDecimal monthlyRent;

    @JsonProperty("move_in_date")
    private LocalDate moveInDate;

    @JsonProperty("move_out_date")
    private LocalDate moveOutDate;

    @JsonProperty("is_active")
    private Boolean isActive;

    public ResidentDTO() {
    }

    public ResidentDTO(Long id, Long propertyId, Long unitId, String firstName, String lastName, String email, String phoneNumber, BigDecimal monthlyRent, LocalDate moveInDate, LocalDate moveOutDate, Boolean isActive) {
        this.id = id;
        this.propertyId = propertyId;
        this.unitId = unitId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.monthlyRent = monthlyRent;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }

    public LocalDate getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(LocalDate moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
