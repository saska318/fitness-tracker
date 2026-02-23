package com.example.fitnesstracker.workout;

import com.example.fitnesstracker.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUser(AppUser user);

    List<Workout> findByUserAndWorkoutDateBetween(
            AppUser user,
            LocalDateTime start,
            LocalDateTime end
    );
}
