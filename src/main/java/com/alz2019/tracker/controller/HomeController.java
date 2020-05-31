package com.alz2019.tracker.controller;

import com.alz2019.tracker.model.RegionalStats;
import com.alz2019.tracker.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CovidService covidService;

    @GetMapping("/")
    public String home(Model model) {
        List<RegionalStats> allStats = covidService.getAllStats();
        int totalActiveCases = allStats.stream()
                .mapToInt(RegionalStats::getActive)
                .sum();
        int totalConfirmedCases = allStats.stream()
                .mapToInt(RegionalStats::getConfirmed)
                .sum();
        model.addAttribute("regionalStats", covidService.getAllStats());
        model.addAttribute("totalActiveCases", totalActiveCases);
        model.addAttribute("totalConfirmedCases", totalConfirmedCases);
        return "home";
    }
}
