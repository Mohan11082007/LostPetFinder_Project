package com.example.lostpetfinder.repository;

import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoundAnimalReportRepository extends JpaRepository<FoundAnimalReport, Long> {
    List<FoundAnimalReport> findByStatusOrderByReportedAtDesc(ReportStatus status);
    List<FoundAnimalReport> findByFoundLocationIgnoreCaseOrderByReportedAtDesc(String location);
    List<FoundAnimalReport> findByFoundLocationIgnoreCaseAndStatusOrderByReportedAtDesc(String location, ReportStatus status);
    List<FoundAnimalReport> findBySpeciesIgnoreCaseAndFoundLocationIgnoreCaseAndStatus(String species, String location, ReportStatus status);
}
