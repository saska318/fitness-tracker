package com.example.fitnesstracker.workout;

import com.example.fitnesstracker.auth.AppUserPrincipal;
import com.example.fitnesstracker.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository repo;

    public void saveWorkout(WorkoutRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal principal = (AppUserPrincipal) auth.getPrincipal();
        AppUser user = principal.getUser();

        Workout w = new Workout();
        w.setUser(user);
        w.setType(request.type());
        w.setCalories(request.calories());
        w.setDurationMinutes(request.durationMinutes());
        w.setIntensity(request.intensity());
        w.setFeeling(request.feeling());
        w.setWorkoutDate(request.workoutDate());

        repo.save(w);
    }
}
