package com.example.fitnesstracker.workout;

import java.time.LocalDateTime;

public record WorkoutRequest(
        WorkoutType type,
        int durationMinutes,
        int intensity,
        int feeling,
        LocalDateTime workoutDate
) {}
