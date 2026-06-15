package com.insurance.application.service;

import com.insurance.application.dto.ApplicationRequest;
import com.insurance.application.dto.ApplicationResponse;
import com.insurance.application.model.InsuranceApplication;
import com.insurance.application.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApplicationService applicationService;

    private ApplicationRequest request;
    private InsuranceApplication savedApplication;

    @BeforeEach
    void setUp() {
        request = new ApplicationRequest();
        request.setFirstName("Sudin");
        request.setLastName("Shrestha");
        request.setPostalCode("51063");
        request.setVehicleType("CAR");
        request.setAnnualMileage(15000);

        savedApplication = new InsuranceApplication();
        savedApplication.setId(1L);
        savedApplication.setFirstName("Sudin");
        savedApplication.setLastName("Shrestha");
        savedApplication.setPostalCode("51063");
        savedApplication.setVehicleType("CAR");
        savedApplication.setAnnualMileage(15000);
        savedApplication.setPremium(BigDecimal.valueOf(195.0));
        savedApplication.setFederalState("Nordrhein-Westfalen");
        savedApplication.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createApplication_Success() {
        // Mock calculation-service response
        Map<String, Object> calcBody = Map.of(
                "premium", 195.0,
                "federalState", "Nordrhein-Westfalen"
        );
        ResponseEntity<Map> calcResponse = new ResponseEntity<>(calcBody, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenReturn(calcResponse);

        when(repository.save(any(InsuranceApplication.class)))
                .thenReturn(savedApplication);

        ApplicationResponse response = applicationService.createApplication(request);

        assertNotNull(response);
        assertEquals("Sudin", response.getFirstName());
        assertEquals("Shrestha", response.getLastName());
        assertEquals(BigDecimal.valueOf(195.0), response.getPremium());
        assertEquals("Nordrhein-Westfalen", response.getFederalState());
        verify(repository, times(1)).save(any(InsuranceApplication.class));
    }

    @Test
    void getAllApplications_Success() {
        when(repository.findAll()).thenReturn(List.of(savedApplication));

        List<ApplicationResponse> result = applicationService.getAllApplications();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sudin", result.get(0).getFirstName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getApplicationById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(savedApplication));

        ApplicationResponse result = applicationService.getApplicationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sudin", result.getFirstName());
    }

    @Test
    void getApplicationById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> applicationService.getApplicationById(99L));

        assertEquals("Application not found with id: 99", exception.getMessage());
    }
}