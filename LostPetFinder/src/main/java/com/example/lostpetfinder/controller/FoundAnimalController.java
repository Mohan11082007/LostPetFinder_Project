package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.dto.FoundAnimalRequest;
import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.service.FoundAnimalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/found-animals")
public class FoundAnimalController {
    private final FoundAnimalService service;
    public FoundAnimalController(FoundAnimalService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public FoundAnimalReport create(@Valid @RequestBody FoundAnimalRequest request) { return service.create(request); }
    @GetMapping public List<FoundAnimalReport> all() { return service.all(); }
    @GetMapping("/{id}") public FoundAnimalReport one(@PathVariable Long id) { return service.find(id); }
    @PutMapping("/{id}") public FoundAnimalReport update(@PathVariable Long id, @Valid @RequestBody FoundAnimalRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
    @PatchMapping("/{id}/resolve") public FoundAnimalReport resolve(@PathVariable Long id) { return service.resolve(id); }
    @GetMapping("/search") public List<FoundAnimalReport> search(@RequestParam String locality) { return service.search(locality); }
}
