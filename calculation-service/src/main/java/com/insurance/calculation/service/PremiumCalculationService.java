package com.insurance.calculation.service;

import com.insurance.calculation.model.PremiumRequest;
import com.insurance.calculation.model.PremiumResponse;
import com.insurance.calculation.util.PostcodeRegionResolver;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PremiumCalculationService {

    private final PostcodeRegionResolver regionResolver;

    // Region factors per federal state
    private static final Map<String, Double> REGION_FACTORS = Map.ofEntries(
            Map.entry("Bayern", 1.3),
            Map.entry("Berlin", 1.5),
            Map.entry("Brandenburg", 1.0),
            Map.entry("Hamburg", 1.4),
            Map.entry("Hessen", 1.2),
            Map.entry("Nordrhein-Westfalen", 1.3),
            Map.entry("Baden-Württemberg", 1.2),
            Map.entry("Sachsen", 1.0),
            Map.entry("Thüringen", 1.0),
            Map.entry("UNKNOWN", 1.0)
    );

    public PremiumCalculationService(PostcodeRegionResolver regionResolver) {
        this.regionResolver = regionResolver;
    }

    public PremiumResponse calculate(PremiumRequest request) {
        double mileageFactor = getMileageFactor(request.getAnnualMileage());
        double vehicleFactor = request.getVehicleType().getFactor();
        String federalState = regionResolver.getFederalState(request.getPostalCode());
        double regionFactor = REGION_FACTORS.getOrDefault(federalState, 1.0);

        double premium = mileageFactor * vehicleFactor * regionFactor * 100;

        return new PremiumResponse(premium, request.getPostalCode(), federalState);
    }

    private double getMileageFactor(int mileage) {
        if (mileage <= 5000) return 0.5;
        else if (mileage <= 10000) return 1.0;
        else if (mileage <= 20000) return 1.5;
        else return 2.0;
    }
}