package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.dto.MatchResponse;
import com.example.lostpetfinder.service.MatchingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchingService service;
    public MatchController(MatchingService service) { this.service = service; }
    @GetMapping("/lost/{lostPetId}") public List<MatchResponse> find(@PathVariable Long lostPetId) { return service.findMatches(lostPetId); }
}
