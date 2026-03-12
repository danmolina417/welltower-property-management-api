package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentRollDTO {
    private LocalDate date;

    @JsonProperty("property_id")
    private Long propertyId;

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("unit_number")
    private String unitNumber;

    @JsonProperty("resident_id")
    private Long residentId;

    @JsonProperty("resident_name")
    private String residentName;

    @JsonProperty("monthly_rent")
    private BigDecimal monthlyRent;
}
