package com.likeherotozero.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;


import com.likeherotozero.model.Emission;
import com.likeherotozero.repository.EmissionRepository;

@RestController
public class EmissionController {

    private final EmissionRepository emissionRepository;

    public EmissionController(EmissionRepository emissionRepository) {
        this.emissionRepository = emissionRepository;
    }

    // PUBLIC DATA – nur genehmigte emissions
    @GetMapping("/api/emissions")
    public List<Emission> getApprovedEmissions() {
        return emissionRepository.findByApprovedTrue();
    }

    @GetMapping("/api/emissions/latest/{countryCode}")
    public Optional<Emission> latestByCountry(@PathVariable String countryCode) {
        return emissionRepository
            .findTopByCountryCodeAndApprovedTrueOrderByEmissionsYearDesc(
                countryCode.toUpperCase()
            );
    }

    @GetMapping("/api/emissions/latest")
    public List<Emission> latestPerCountry() {
    	return emissionRepository.findLatestApprovedPerCountry();
    }
    
    // SCIENTIST SUBMISSION – Genehmigung notwendig
    @PostMapping("/api/emissions")
    public Emission createEmission(@ModelAttribute Emission emission) {
        emission.setApproved(false);
        return emissionRepository.save(emission);
    }

}
