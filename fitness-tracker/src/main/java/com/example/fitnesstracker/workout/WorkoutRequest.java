package com.example.fitnesstracker.workout;

import java.time.LocalDateTime;

public record WorkoutRequest(
        WorkoutType type,
        int durationMinutes,
        int calories,
        int intensity,
        int feeling,
        String notes,
        LocalDateTime workoutDate
) {}
