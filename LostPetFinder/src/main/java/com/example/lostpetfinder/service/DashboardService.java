package com.example.lostpetfinder.service;

import com.example.lostpetfinder.dto.DashboardResponse;
import com.example.lostpetfinder.entity.ReportStatus;
import com.example.lostpetfinder.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final LostPetService lostPetService;
    private final FoundAnimalService foundAnimalService;
    private final UserRepository userRepository;
    public DashboardService(LostPetService l, FoundAnimalService f, UserRepository u) { this.lostPetService = l; this.foundAnimalService = f; this.userRepository = u; }
    public DashboardResponse summary() {
        long activeLost = lostPetService.count(ReportStatus.ACTIVE), resolvedLost = lostPetService.count(ReportStatus.RESOLVED);
        long activeFound = foundAnimalService.count(ReportStatus.ACTIVE), resolvedFound = foundAnimalService.count(ReportStatus.RESOLVED);
        return new DashboardResponse(activeLost + resolvedLost, activeLost, resolvedLost,
                activeFound + resolvedFound, activeFound, resolvedFound, userRepository.count());
    }
}
