package com.insurance.calculation.util;

import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class PostcodeRegionResolver {

    private final Map<String, String> postcodeFederalStateMap = new HashMap<>();

    public PostcodeRegionResolver() {
        loadPostcodes();
    }

    private void loadPostcodes() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/postcodes.csv")))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }

                // CSV hat Komma als Trennzeichen, Felder in Anführungszeichen
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    // Spalte 3 = REGION1 (Bundesland), Spalte 7 = POSTLEITZAHL
                    String federalState = parts[2].trim().replace("\"", "");
                    String postcode = parts[6].trim().replace("\"", "");

                    if (!postcode.isEmpty() && !federalState.isEmpty()) {
                        postcodeFederalStateMap.put(postcode, federalState);
                    }
                }
            }
            System.out.println("CSV loaded: " + postcodeFederalStateMap.size() + " postcodes");
        } catch (Exception e) {
            System.out.println("CSV not loaded: " + e.getMessage());
        }
    }

    public String getFederalState(String postcode) {
        return postcodeFederalStateMap.getOrDefault(postcode, "UNKNOWN");
    }
}