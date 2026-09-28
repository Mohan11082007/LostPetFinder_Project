package com.example.lostpetfinder.dto;

public record DashboardResponse(
        long totalLost,
        long activeLost,
        long resolvedLost,
        long totalFound,
        long activeFound,
        long resolvedFound,
        long totalUsers
) {}
