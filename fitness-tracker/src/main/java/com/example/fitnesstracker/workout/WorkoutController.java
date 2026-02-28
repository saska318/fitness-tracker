package com.example.fitnesstracker.workout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService service;

    @PostMapping
    public void save(@RequestBody WorkoutRequest request) {
        service.saveWorkout(request);
    }
}
