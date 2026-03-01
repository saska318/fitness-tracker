package com.example.fitnesstracker.config;

import com.example.fitnesstracker.user.AppUser;
import com.example.fitnesstracker.user.UserRepository;
import com.example.fitnesstracker.workout.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) return;

        AppUser demoUser = new AppUser();
        demoUser.setEmail("saska@fit.com");
        demoUser.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(demoUser);

        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 320, 30, 8, 7,
                LocalDateTime.of(2026, 1, 5, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.STRENGTH, 360, 45, 9, 10,
                LocalDateTime.of(2026, 1, 12, 18, 30)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.FLEXIBILITY, 180, 20, 6, 8,
                LocalDateTime.of(2026, 1, 20, 19, 0)));

        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 350, 30, 7, 6,
                LocalDateTime.of(2026, 2, 2, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.STRENGTH, 370, 45, 6, 8,
                LocalDateTime.of(2026, 2, 4, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.FLEXIBILITY, 240, 40, 5, 9,
                LocalDateTime.of(2026, 2, 5, 11, 0)));

        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 390, 35, 8, 8,
                LocalDateTime.of(2026, 2, 10, 18, 30)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.FLEXIBILITY, 210, 25, 5, 7,
                LocalDateTime.of(2026, 2, 12, 19, 0)));

        workoutRepository.save(createWorkout(demoUser, WorkoutType.STRENGTH, 420, 50, 9, 9,
                LocalDateTime.of(2026, 2, 17, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 360, 30, 9, 7,
                LocalDateTime.of(2026, 2, 19, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.STRENGTH, 340, 70, 7, 8,
                LocalDateTime.of(2026, 2, 20, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.FLEXIBILITY, 200, 35, 6, 10,
                LocalDateTime.of(2026, 2, 21, 11, 0)));

        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 410, 40, 10, 8,
                LocalDateTime.of(2026, 2, 24, 18, 0)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.FLEXIBILITY, 200, 20, 7, 10,
                LocalDateTime.of(2026, 2, 26, 19, 0)));

        AppUser secondUser = new AppUser();
        secondUser.setEmail("demo@fit.com");
        secondUser.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(secondUser);

        workoutRepository.save(createWorkout(secondUser, WorkoutType.FLEXIBILITY, 230, 20, 3, 4, LocalDateTime.now().minusDays(1)));
        workoutRepository.save(createWorkout(secondUser, WorkoutType.CARDIO, 400, 25, 3, 3, LocalDateTime.now().minusDays(4)));
    }

    private Workout createWorkout(AppUser user,
                                  WorkoutType type,
                                  int calories,
                                  int duration,
                                  int intensity,
                                  int feeling,
                                  LocalDateTime date) {

        Workout w = new Workout();
        w.setUser(user);
        w.setType(type);
        w.setCalories(calories);
        w.setDurationMinutes(duration);
        w.setIntensity(intensity);
        w.setFeeling(feeling);
        w.setWorkoutDate(date);
        return w;
    }
}
