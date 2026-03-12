package com.welltower.propertymanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "residents", indexes = {
    @Index(name = "idx_resident_property", columnList = "property_id"),
    @Index(name = "idx_resident_unit", columnList = "unit_id"),
    @Index(name = "idx_resident_move_dates", columnList = "move_in_date, move_out_date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long residentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column(nullable = false)
    private BigDecimal monthlyRent;

    @Column(nullable = false)
    private LocalDate moveInDate;

    @Column
    private LocalDate moveOutDate;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
