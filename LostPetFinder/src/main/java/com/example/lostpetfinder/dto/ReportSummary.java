package com.example.lostpetfinder.dto;

import java.time.LocalDateTime;

public record ReportSummary(
        String type,
        Long id,
        String species,
        String breed,
        String color,
        String location,
        String status,
        LocalDateTime reportedAt
) {}
