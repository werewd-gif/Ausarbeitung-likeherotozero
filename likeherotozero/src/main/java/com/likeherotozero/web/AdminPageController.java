package com.likeherotozero.web;

import com.likeherotozero.repository.EmissionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final EmissionRepository emissionRepository;

    public AdminPageController(EmissionRepository emissionRepository) {
        this.emissionRepository = emissionRepository;
    }

    // Seite anzeigen: pending Emissions in Tabelle
    @GetMapping("/emissions")
    public String adminEmissions(Model model) {
        model.addAttribute("emissions", emissionRepository.findByApprovedFalse());
        return "admin-emissions"; // templates/admin-emissions.html
    }

    // Approve Button
    @PostMapping("/emissions/{id}/approve")
    public String approve(@PathVariable Long id) {
        var emission = emissionRepository.findById(id).orElseThrow();
        emission.setApproved(true);
        emissionRepository.save(emission);
        return "redirect:/admin/emissions";
    }
}
