package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.dto.ReportSummary;
import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.service.FoundAnimalService;
import com.example.lostpetfinder.service.LostPetService;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class SearchController {
    private final LostPetService lostService;
    private final FoundAnimalService foundService;
    public SearchController(LostPetService l, FoundAnimalService f) { this.lostService = l; this.foundService = f; }

    @GetMapping("/search")
    public List<ReportSummary> search(@RequestParam String locality) {
        List<ReportSummary> result = new ArrayList<>();
        for (LostPetReport r : lostService.search(locality)) {
            result.add(new ReportSummary("LOST", r.getId(), r.getSpecies(), r.getBreed(), r.getColor(), r.getLastSeenLocation(), r.getStatus().name(), r.getReportedAt()));
        }
        for (FoundAnimalReport r : foundService.search(locality)) {
            result.add(new ReportSummary("FOUND", r.getId(), r.getSpecies(), r.getBreed(), r.getColor(), r.getFoundLocation(), r.getStatus().name(), r.getReportedAt()));
        }
        result.sort(Comparator.comparing(ReportSummary::reportedAt).reversed());
        return result;
    }
}
