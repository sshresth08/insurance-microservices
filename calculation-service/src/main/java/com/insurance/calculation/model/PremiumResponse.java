package com.insurance.calculation.model;

public class PremiumResponse {

    private double premium;
    private String region;
    private String federalState;

    public PremiumResponse(double premium, String region, String federalState) {
        this.premium = premium;
        this.region = region;
        this.federalState = federalState;
    }

    public double getPremium() { return premium; }
    public String getRegion() { return region; }
    public String getFederalState() { return federalState; }
}