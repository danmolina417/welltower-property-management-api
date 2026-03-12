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
public class ResidentDTO {
    @JsonProperty("resident_id")
    private Long residentId;

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
}
