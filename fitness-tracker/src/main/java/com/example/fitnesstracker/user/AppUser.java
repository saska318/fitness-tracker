package com.example.fitnesstracker.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "app_user_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
}
