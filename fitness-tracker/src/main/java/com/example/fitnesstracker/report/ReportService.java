package com.example.fitnesstracker.report;

import com.example.fitnesstracker.auth.AppUserPrincipal;
import com.example.fitnesstracker.user.AppUser;
import com.example.fitnesstracker.user.UserRepository;
import com.example.fitnesstracker.workout.Workout;
import com.example.fitnesstracker.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final WorkoutRepository workoutRepo;
    private final UserRepository appUserRepository;

    public List<WeeklyReportDto> getMonthlyReport(int year, int month) {
        AppUser user = getAuthenticatedUser();

        List<Workout> workouts = getWorkoutsForMonth(user, year, month);

        return workouts.stream()
                .collect(Collectors.groupingBy(this::getWeekOfMonth))
                .entrySet()
                .stream()
                .map(entry -> createWeeklyReport(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(WeeklyReportDto::weekNumber))
                .toList();
    }

    private AppUser getAuthenticatedUser() {
        var principal = (AppUserPrincipal) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return appUserRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private List<Workout> getWorkoutsForMonth(AppUser user, int year, int month) {
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        return workoutRepo.findByUserAndWorkoutDateBetween(user, start, end);
    }

    private int getWeekOfMonth(Workout w) {
        return w.getWorkoutDate().get(WeekFields.ISO.weekOfMonth());
    }

    private WeeklyReportDto createWeeklyReport(int week, List<Workout> workouts) {
        long count = workouts.size();
        int totalDuration = workouts.stream()
                .mapToInt(Workout::getDurationMinutes)
                .sum();
        double avgIntensity = workouts.stream()
                .mapToInt(Workout::getIntensity)
                .average()
                .orElse(0);
        double avgFeeling = workouts.stream()
                .mapToInt(Workout::getFeeling)
                .average()
                .orElse(0);

        return new WeeklyReportDto(week, count, totalDuration, avgIntensity, avgFeeling);
    }
}
