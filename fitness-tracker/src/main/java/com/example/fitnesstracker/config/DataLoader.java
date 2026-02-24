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

        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 30, 4, 5, LocalDateTime.now().minusDays(1)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.STRENGTH, 45, 5, 4, LocalDateTime.now().minusDays(2)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.FLEXIBILITY, 25, 3, 5, LocalDateTime.now().minusDays(3)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.CARDIO, 35, 4, 4, LocalDateTime.now().minusDays(5)));
        workoutRepository.save(createWorkout(demoUser, WorkoutType.STRENGTH, 50, 5, 5, LocalDateTime.now().minusDays(7)));

        AppUser secondUser = new AppUser();
        secondUser.setEmail("demo@fit.com");
        secondUser.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(secondUser);

        workoutRepository.save(createWorkout(secondUser, WorkoutType.FLEXIBILITY, 20, 3, 4, LocalDateTime.now().minusDays(1)));
        workoutRepository.save(createWorkout(secondUser, WorkoutType.CARDIO, 25, 3, 3, LocalDateTime.now().minusDays(4)));
    }

    private Workout createWorkout(AppUser user,
                                  WorkoutType type,
                                  int duration,
                                  int intensity,
                                  int feeling,
                                  LocalDateTime date) {

        Workout w = new Workout();
        w.setUser(user);
        w.setType(type);
        w.setDurationMinutes(duration);
        w.setIntensity(intensity);
        w.setFeeling(feeling);
        w.setWorkoutDate(date);
        return w;
    }
}
