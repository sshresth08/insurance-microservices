package com.insurance.calculation.controller;

import com.insurance.calculation.model.PremiumRequest;
import com.insurance.calculation.model.PremiumResponse;
import com.insurance.calculation.service.PremiumCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premium")
@Tag(name = "Premium Calculation", description = "API for calculating insurance premiums")
public class PremiumController {

    private final PremiumCalculationService calculationService;

    public PremiumController(PremiumCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Operation(summary = "Calculate insurance premium")
    @PostMapping("/calculate")
    public ResponseEntity<PremiumResponse> calculatePremium(
            @RequestBody PremiumRequest request) {
        return ResponseEntity.ok(calculationService.calculate(request));
    }

    @Operation(summary = "Health check")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Calculation Service is running!");
    }
}