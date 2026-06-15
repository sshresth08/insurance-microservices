package com.insurance.calculation.model;

public class PremiumRequest {

    private int annualMileage;
    private String postalCode;
    private VehicleType vehicleType;

    public int getAnnualMileage() { return annualMileage; }
    public void setAnnualMileage(int annualMileage) { this.annualMileage = annualMileage; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
}