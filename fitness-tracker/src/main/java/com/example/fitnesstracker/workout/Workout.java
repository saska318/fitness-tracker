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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutType type;

    private int calories;

    private int durationMinutes;

    private int intensity;

    private int feeling;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime workoutDate;
}
