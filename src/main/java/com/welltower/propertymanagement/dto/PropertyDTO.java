package com.welltower.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDTO {
    @JsonProperty("property_id")
    private Long propertyId;

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
}
