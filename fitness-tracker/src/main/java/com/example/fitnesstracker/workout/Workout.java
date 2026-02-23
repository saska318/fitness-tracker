package com.example.fitnesstracker.workout;

import com.example.fitnesstracker.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Workout {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private WorkoutType type;

    private int durationMinutes;
    private int intensity;
    private int feeling;

    private LocalDateTime workoutDate;
}
