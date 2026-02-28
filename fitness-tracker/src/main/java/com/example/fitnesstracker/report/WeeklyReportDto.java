package com.example.fitnesstracker.report;

public record WeeklyReportDto(
        int weekNumber,
        long totalWorkouts,
        int totalDuration,
        double avgIntensity,
        double avgFeeling
) {
}
