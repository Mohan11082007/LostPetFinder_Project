package com.example.lostpetfinder.dto;

public record MatchResponse(
        Long foundReportId,
        String species,
        String breed,
        String color,
        String location,
        String description,
        int matchScore,
        String matchReason
) {}
