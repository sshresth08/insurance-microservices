package com.insurance.application.service;

import com.insurance.application.dto.ApplicationRequest;
import com.insurance.application.dto.ApplicationResponse;
import com.insurance.application.model.InsuranceApplication;
import com.insurance.application.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final RestTemplate restTemplate;
    @Value("${calculation.service.url:http://localhost:8081}")
    private String calculationServiceUrl;

    public ApplicationService(ApplicationRepository repository,
                              RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public ApplicationResponse createApplication(ApplicationRequest request) {
        // 1. Prämie vom calculation-service holen
        Map<String, Object> calcRequest = new HashMap<>();
        calcRequest.put("annualMileage", request.getAnnualMileage());
        calcRequest.put("postalCode", request.getPostalCode());
        calcRequest.put("vehicleType", request.getVehicleType());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(calcRequest, headers);

        ResponseEntity<Map> calcResponse = restTemplate.postForEntity(
                calculationServiceUrl + "/api/premium/calculate",
                entity, Map.class);

        Double premium = (Double) calcResponse.getBody().get("premium");
        String federalState = (String) calcResponse.getBody().get("federalState");

        // 2. Antrag in Supabase speichern
        InsuranceApplication application = new InsuranceApplication();
        application.setFirstName(request.getFirstName());
        application.setLastName(request.getLastName());
        application.setPostalCode(request.getPostalCode());
        application.setVehicleType(request.getVehicleType());
        application.setAnnualMileage(request.getAnnualMileage());
        application.setPremium(BigDecimal.valueOf(premium));
        application.setFederalState(federalState);

        InsuranceApplication saved = repository.save(application);

        return new ApplicationResponse(
                saved.getId(), saved.getFirstName(), saved.getLastName(),
                saved.getPostalCode(), saved.getVehicleType(),
                saved.getAnnualMileage(), saved.getPremium(),
                saved.getFederalState(), saved.getCreatedAt()
        );
    }

    public List<ApplicationResponse> getAllApplications() {
        return repository.findAll()
                .stream()
                .map(app -> new ApplicationResponse(
                        app.getId(), app.getFirstName(), app.getLastName(),
                        app.getPostalCode(), app.getVehicleType(),
                        app.getAnnualMileage(), app.getPremium(),
                        app.getFederalState(), app.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public ApplicationResponse getApplicationById(Long id) {
        InsuranceApplication app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        return new ApplicationResponse(
                app.getId(), app.getFirstName(), app.getLastName(),
                app.getPostalCode(), app.getVehicleType(),
                app.getAnnualMileage(), app.getPremium(),
                app.getFederalState(), app.getCreatedAt()
        );
    }
}