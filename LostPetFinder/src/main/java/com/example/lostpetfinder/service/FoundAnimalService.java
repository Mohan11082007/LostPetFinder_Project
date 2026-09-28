package com.example.lostpetfinder.service;

import com.example.lostpetfinder.dto.FoundAnimalRequest;
import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.entity.ReportStatus;
import com.example.lostpetfinder.entity.User;
import com.example.lostpetfinder.exception.ResourceNotFoundException;
import com.example.lostpetfinder.repository.FoundAnimalReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoundAnimalService {
    private final FoundAnimalReportRepository repository;
    private final UserService userService;
    public FoundAnimalService(FoundAnimalReportRepository repository, UserService userService) { this.repository = repository; this.userService = userService; }

    public FoundAnimalReport create(FoundAnimalRequest request) {
        User user = userService.findById(request.userId());
        FoundAnimalReport report = new FoundAnimalReport();
        report.setSpecies(request.species().trim()); report.setBreed(clean(request.breed()));
        report.setColor(request.color().trim()); report.setFoundLocation(request.foundLocation().trim());
        report.setDescription(clean(request.description())); report.setUser(user); report.setStatus(ReportStatus.ACTIVE);
        return repository.save(report);
    }
    public List<FoundAnimalReport> all() { return repository.findAll(); }
    public FoundAnimalReport find(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Found animal report not found: " + id)); }
    public FoundAnimalReport update(Long id, FoundAnimalRequest request) {
        FoundAnimalReport r = find(id);
        r.setSpecies(request.species().trim()); r.setBreed(clean(request.breed()));
        r.setColor(request.color().trim()); r.setFoundLocation(request.foundLocation().trim());
        r.setDescription(clean(request.description())); r.setUser(userService.findById(request.userId()));
        return repository.save(r);
    }
    public FoundAnimalReport resolve(Long id) { FoundAnimalReport r = find(id); r.setStatus(ReportStatus.RESOLVED); return repository.save(r); }
    public void delete(Long id) { repository.delete(find(id)); }
    public List<FoundAnimalReport> search(String location) { return repository.findByFoundLocationIgnoreCaseOrderByReportedAtDesc(location.trim()); }
    public long count(ReportStatus status) { return repository.findByStatusOrderByReportedAtDesc(status).size(); }
    private String clean(String s) { return s == null ? null : s.trim(); }
}
