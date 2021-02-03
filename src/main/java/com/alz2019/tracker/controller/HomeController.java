package com.alz2019.tracker.controller;

import com.alz2019.tracker.model.Region;
import com.alz2019.tracker.service.RegionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final RegionService regions;

    public HomeController(RegionService regions) {
        this.regions = regions;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Region> allStats = regions.getAllStats();
        int totalActiveCases = allStats.stream()
                .mapToInt(Region::getActive)
                .sum();
        int totalConfirmedCases = allStats.stream()
                .mapToInt(Region::getConfirmed)
                .sum();
        model.addAttribute("totalActiveCases", totalActiveCases);
        model.addAttribute("totalConfirmedCases", totalConfirmedCases);
        model.addAttribute("stats", regions.getAllStats());
        return "home";
    }
}
