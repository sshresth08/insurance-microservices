package com.insurance.application.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications", schema = "insurance")
public class InsuranceApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "annual_mileage", nullable = false)
    private Integer annualMileage;

    @Column(name = "premium", nullable = false)
    private BigDecimal premium;

    @Column(name = "federal_state")
    private String federalState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setAnnualMileage(Integer annualMileage) { this.annualMileage = annualMileage; }
    public void setPremium(BigDecimal premium) { this.premium = premium; }
    public void setFederalState(String federalState) { this.federalState = federalState; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}