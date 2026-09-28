package com.example.lostpetfinder.service;

import com.example.lostpetfinder.dto.MatchResponse;
import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.entity.ReportStatus;
import com.example.lostpetfinder.exception.ResourceNotFoundException;
import com.example.lostpetfinder.repository.FoundAnimalReportRepository;
import com.example.lostpetfinder.repository.LostPetReportRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class MatchingService {
    private final LostPetReportRepository lostRepository;
    private final FoundAnimalReportRepository foundRepository;

    public MatchingService(LostPetReportRepository lostRepository, FoundAnimalReportRepository foundRepository) {
        this.lostRepository = lostRepository; this.foundRepository = foundRepository;
    }

    public List<MatchResponse> findMatches(Long lostPetId) {
        LostPetReport lost = lostRepository.findById(lostPetId)
                .orElseThrow(() -> new ResourceNotFoundException("Lost pet report not found: " + lostPetId));
        if (lost.getStatus() == ReportStatus.RESOLVED) {
            return List.of();
        }
        List<FoundAnimalReport> candidates = foundRepository.findBySpeciesIgnoreCaseAndFoundLocationIgnoreCaseAndStatus(
                lost.getSpecies(), lost.getLastSeenLocation(), ReportStatus.ACTIVE);
        return candidates.stream().map(found -> {
            int score = 60;
            StringBuilder reason = new StringBuilder("Species + locality matched");
            if (same(lost.getColor(), found.getColor())) { score += 25; reason.append(" + colour"); }
            if (same(lost.getBreed(), found.getBreed())) { score += 15; reason.append(" + breed"); }
            return new MatchResponse(found.getId(), found.getSpecies(), found.getBreed(), found.getColor(),
                    found.getFoundLocation(), found.getDescription(), score, reason.toString());
        }).sorted(Comparator.comparingInt(MatchResponse::matchScore).reversed()).toList();
    }

    private boolean same(String a, String b) {
        if (a == null || b == null || a.isBlank() || b.isBlank()) return false;
        return a.trim().toLowerCase(Locale.ROOT).equals(b.trim().toLowerCase(Locale.ROOT));
    }
}
