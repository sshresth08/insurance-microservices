package com.insurance.calculation.model;

public enum VehicleType {
    MOTORCYCLE(0.5),
    CAR(1.0),
    TRUCK(2.0),
    VAN(1.5);

    private final double factor;

    VehicleType(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
