package com.insurance.application.dto;
import jakarta.validation.constraints.*;

public class ApplicationRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "\\d{5}", message = "Postal code must be exactly 5 digits")
    private String postalCode;

    @NotBlank(message = "Vehicle type is required")
    @Pattern(regexp = "MOTORCYCLE|CAR|VAN|TRUCK", message = "Vehicle type must be MOTORCYCLE, CAR, VAN or TRUCK")
    private String vehicleType;

    @NotNull(message = "Annual mileage is required")
    @Min(value = 1, message = "Annual mileage must be greater than 0")
    @Max(value = 500000, message = "Annual mileage must be less than 500000")
    private Integer annualMileage;

    // Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public Integer getAnnualMileage() { return annualMileage; }
    public void setAnnualMileage(Integer annualMileage) { this.annualMileage = annualMileage; }
}