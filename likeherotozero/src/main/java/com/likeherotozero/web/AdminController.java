package com.likeherotozero.web;

import com.likeherotozero.model.Emission;
import com.likeherotozero.repository.EmissionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/emissions")
public class AdminController {

    private final EmissionRepository emissionRepository;

    public AdminController(EmissionRepository emissionRepository) {
        this.emissionRepository = emissionRepository;
    }

    // Zeigt alle noch nicht freigegebenen Emissionen
    @GetMapping("/pending")
    public Iterable<Emission> pendingEmissions() {
        return emissionRepository.findByApprovedFalse();
    }

    // Gibt eine Emission frei
    @GetMapping("/{id}/approve")
    public Emission approve(@PathVariable Long id) {
        Emission emission = emissionRepository.findById(id)
                .orElseThrow();

        emission.setApproved(true);
        return emissionRepository.save(emission);
    }
}
