package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitDTO {
    @JsonProperty("property_id")
    private Long propertyId;

    @JsonProperty("unit_id")
    private Long unitId;

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
}
