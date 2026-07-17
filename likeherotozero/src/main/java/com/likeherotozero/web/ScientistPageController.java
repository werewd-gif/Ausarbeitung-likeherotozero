package com.likeherotozero.web;

import com.likeherotozero.model.Emission;
import com.likeherotozero.repository.EmissionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScientistPageController {

    private final EmissionRepository emissionRepository;

    public ScientistPageController(EmissionRepository emissionRepository) {
        this.emissionRepository = emissionRepository;
    }

    @GetMapping("/add-emission")
    public String addEmissionForm() {
        return "add-emission";
    }

    @PostMapping("/add-emission")
    public String submitEmission(Emission emission) {
        emission.setApproved(false);
        emissionRepository.save(emission);
        return "redirect:/add-emission?success";
    }
}

