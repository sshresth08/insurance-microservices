package com.insurance.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.application.dto.ApplicationRequest;
import com.insurance.application.dto.ApplicationResponse;
import com.insurance.application.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApplicationService applicationService;

    private ApplicationResponse mockResponse() {
        return new ApplicationResponse(
                1L, "Sudin", "Shrestha",
                "51063", "CAR", 15000,
                BigDecimal.valueOf(195.0),
                "Nordrhein-Westfalen",
                LocalDateTime.now()
        );
    }

    @Test
    void createApplication_Success() throws Exception {
        ApplicationRequest request = new ApplicationRequest();
        request.setFirstName("Sudin");
        request.setLastName("Shrestha");
        request.setPostalCode("51063");
        request.setVehicleType("CAR");
        request.setAnnualMileage(15000);

        when(applicationService.createApplication(any())).thenReturn(mockResponse());

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sudin"))
                .andExpect(jsonPath("$.premium").value(195.0))
                .andExpect(jsonPath("$.federalState").value("Nordrhein-Westfalen"));
    }

    @Test
    void createApplication_ValidationFail_EmptyFirstName() throws Exception {
        ApplicationRequest request = new ApplicationRequest();
        request.setFirstName("");
        request.setLastName("Shrestha");
        request.setPostalCode("51063");
        request.setVehicleType("CAR");
        request.setAnnualMileage(15000);

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    void createApplication_ValidationFail_InvalidPostalCode() throws Exception {
        ApplicationRequest request = new ApplicationRequest();
        request.setFirstName("Sudin");
        request.setLastName("Shrestha");
        request.setPostalCode("ABC");
        request.setVehicleType("CAR");
        request.setAnnualMileage(15000);

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.postalCode")
                        .value("Postal code must be exactly 5 digits"));
    }

    @Test
    void getAllApplications_Success() throws Exception {
        when(applicationService.getAllApplications()).thenReturn(List.of(mockResponse()));

        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Sudin"))
                .andExpect(jsonPath("$[0].premium").value(195.0));
    }

    @Test
    void getApplicationById_Success() throws Exception {
        when(applicationService.getApplicationById(1L)).thenReturn(mockResponse());

        mockMvc.perform(get("/api/applications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Sudin"));
    }

    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(get("/api/applications/health"))
                .andExpect(status().isOk());
    }
}