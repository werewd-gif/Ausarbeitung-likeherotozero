package com.likeherotozero.web;

import com.likeherotozero.model.Emission;
import com.likeherotozero.repository.EmissionRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    private final EmissionRepository emissionRepository;

    public PublicController(EmissionRepository emissionRepository) {
        this.emissionRepository = emissionRepository;
    }

    @GetMapping("/emissions/{countryCode}")
    public String getLatestEmission(@PathVariable String countryCode) {

    	return emissionRepository
    		    .findTopByCountryCodeAndApprovedTrueOrderByEmissionsYearDesc(countryCode.toUpperCase())
                .map(this::formatEmission)
                .orElse("No emission data found for country code: " + countryCode);
    }

    private String formatEmission(Emission emission) {
        return """
            <h2>Latest CO₂ emissions</h2>
            <p>
                <strong>%s</strong> (%d): %,.0f kt CO₂
            </p>

            <hr>

            <p>
                <a href="/">→ Search another country</a>
            </p>

            <p>
                <a href="/add-emission.html">→ Add new emission data (scientist)</a>
            </p>
            """.formatted(
                emission.getCountryName(),
                emission.getEmissionsYear(),
                emission.getCo2EmissionsKt()
            );
    }

}
