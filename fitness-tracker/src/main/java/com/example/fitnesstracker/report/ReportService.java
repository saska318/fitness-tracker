package com.example.fitnesstracker.report;

import com.example.fitnesstracker.user.AppUser;
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

    public List<WeeklyReportDto> getMonthlyReport(int year, int month) {

        AppUser user = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        List<Workout> workouts =
                workoutRepo.findByUserAndWorkoutDateBetween(user, start, end);

        WeekFields weekFields = WeekFields.ISO;

        Map<Integer, List<Workout>> grouped =
                workouts.stream()
                        .collect(Collectors.groupingBy(w ->
                                w.getWorkoutDate()
                                        .get(weekFields.weekOfMonth())
                        ));

        List<WeeklyReportDto> result = new ArrayList<>();

        for (Map.Entry<Integer, List<Workout>> entry : grouped.entrySet()) {

            int week = entry.getKey();
            List<Workout> ws = entry.getValue();

            long count = ws.size();

            int totalDuration = ws.stream()
                    .mapToInt(Workout::getDurationMinutes)
                    .sum();

            double avgIntensity = ws.stream()
                    .mapToInt(Workout::getIntensity)
                    .average().orElse(0);

            double avgFeeling = ws.stream()
                    .mapToInt(Workout::getFeeling)
                    .average().orElse(0);

            result.add(new WeeklyReportDto(
                    week, count, totalDuration, avgIntensity, avgFeeling
            ));
        }

        result.sort(Comparator.comparingInt(WeeklyReportDto::weekNumber));

        return result;
    }
}
