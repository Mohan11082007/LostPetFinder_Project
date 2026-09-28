package com.example.lostpetfinder.service;

import com.example.lostpetfinder.dto.LostPetRequest;
import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.entity.ReportStatus;
import com.example.lostpetfinder.entity.User;
import com.example.lostpetfinder.exception.ResourceNotFoundException;
import com.example.lostpetfinder.repository.LostPetReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LostPetService {

    private final LostPetReportRepository repository;
    private final UserService userService;

    public LostPetService(
            LostPetReportRepository repository,
            UserService userService) {

        this.repository = repository;
        this.userService = userService;
    }

    // CREATE
    public LostPetReport create(LostPetRequest request) {

        User user = userService.findById(request.userId());

        LostPetReport report = new LostPetReport();

        report.setSpecies(request.species().trim());
        report.setBreed(clean(request.breed()));
        report.setColor(request.color().trim());
        report.setLastSeenLocation(request.lastSeenLocation().trim());

        report.setUser(user);

        // Set default values automatically
        report.setStatus(ReportStatus.ACTIVE);
        report.setReportedAt(LocalDateTime.now());

        return repository.save(report);
    }

    // GET ALL
    public List<LostPetReport> all() {
        return repository.findAll();
    }

    // GET BY ID
    public LostPetReport find(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Lost pet report not found: " + id));
    }

    // UPDATE
    public LostPetReport update(
            Long id,
            LostPetRequest request) {

        LostPetReport r = find(id);

        r.setSpecies(request.species().trim());
        r.setBreed(clean(request.breed()));
        r.setColor(request.color().trim());
        r.setLastSeenLocation(request.lastSeenLocation().trim());

        r.setUser(userService.findById(request.userId()));

        return repository.save(r);
    }

    // RESOLVE
    public LostPetReport resolve(Long id) {

        LostPetReport r = find(id);

        r.setStatus(ReportStatus.RESOLVED);

        return repository.save(r);
    }

    // DELETE
    public void delete(Long id) {

        repository.delete(find(id));
    }

    // SEARCH
    public List<LostPetReport> search(String location) {

        return repository
                .findByLastSeenLocationIgnoreCaseOrderByReportedAtDesc(
                        location.trim());
    }

    // COUNT
    public long count(ReportStatus status) {

        return repository
                .findByStatusOrderByReportedAtDesc(status)
                .size();
    }

    // CLEAN OPTIONAL STRING
    private String clean(String s) {

        return s == null ? null : s.trim();
    }
}