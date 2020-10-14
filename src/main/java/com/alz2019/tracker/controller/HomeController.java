package com.alz2019.tracker.controller;

import com.alz2019.tracker.dao.RegionDao;
import com.alz2019.tracker.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    RegionDao regions;

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
