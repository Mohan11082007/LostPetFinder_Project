package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.dto.LostPetRequest;
import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.service.LostPetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lost-pets")
public class LostPetController {

    private final LostPetService service;

    public LostPetController(LostPetService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LostPetReport create(@Valid @RequestBody LostPetRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<LostPetReport> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public LostPetReport one(@PathVariable Long id) {
        return service.find(id);
    }

    @PutMapping("/{id}")
    public LostPetReport update(
            @PathVariable Long id,
            @Valid @RequestBody LostPetRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/resolve")
    public Map<String, Object> resolve(@PathVariable Long id) {

        LostPetReport report = service.resolve(id);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("message", "Lost pet report resolved successfully");
        response.put("id", report.getId());
        response.put("species", report.getSpecies());
        response.put("breed", report.getBreed());
        response.put("color", report.getColor());
        response.put("lastSeenLocation", report.getLastSeenLocation());
        response.put("status", report.getStatus());

        return response;
    }

    @GetMapping("/search")
    public List<LostPetReport> search(@RequestParam String locality) {
        return service.search(locality);
    }
}