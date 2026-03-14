package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentRollDTO {
    private LocalDate date;

    @JsonProperty("property_id")
    private Long propertyId;

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("unit_number")
    private String unitNumber;

    @JsonProperty("resident_id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long residentId;

    @JsonProperty("resident_name")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String residentName;

    @JsonProperty("monthly_rent")
    private BigDecimal monthlyRent;

    @JsonProperty("unit_status")
    private String unitStatus;

    public RentRollDTO() {
    }

    public RentRollDTO(LocalDate date, Long propertyId, Long unitId, String unitNumber, Long residentId, String residentName, BigDecimal monthlyRent, String unitStatus) {
        this.date = date;
        this.propertyId = propertyId;
        this.unitId = unitId;
        this.unitNumber = unitNumber;
        this.residentId = residentId;
        this.residentName = residentName;
        this.monthlyRent = monthlyRent;
        this.unitStatus = unitStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }
}
