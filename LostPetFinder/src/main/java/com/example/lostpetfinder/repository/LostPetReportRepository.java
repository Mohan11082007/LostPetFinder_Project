package com.example.lostpetfinder.repository;

import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LostPetReportRepository extends JpaRepository<LostPetReport, Long> {
    List<LostPetReport> findByStatusOrderByReportedAtDesc(ReportStatus status);
    List<LostPetReport> findByLastSeenLocationIgnoreCaseOrderByReportedAtDesc(String location);
    List<LostPetReport> findByLastSeenLocationIgnoreCaseAndStatusOrderByReportedAtDesc(String location, ReportStatus status);
}
