package com.insurance.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApplicationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String vehicleType;
    private Integer annualMileage;
    private BigDecimal premium;
    private String federalState;
    private LocalDateTime createdAt;

    public ApplicationResponse(Long id, String firstName, String lastName,
                               String postalCode, String vehicleType,
                               Integer annualMileage, BigDecimal premium,
                               String federalState, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.vehicleType = vehicleType;
        this.annualMileage = annualMileage;
        this.premium = premium;
        this.federalState = federalState;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPostalCode() { return postalCode; }
    public String getVehicleType() { return vehicleType; }
    public Integer getAnnualMileage() { return annualMileage; }
    public BigDecimal getPremium() { return premium; }
    public String getFederalState() { return federalState; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}