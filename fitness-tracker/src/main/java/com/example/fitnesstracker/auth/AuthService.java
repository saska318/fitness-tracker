package com.example.fitnesstracker.auth;

import com.example.fitnesstracker.user.AppUser;
import com.example.fitnesstracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String register(String email, String password) {
        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        repo.save(user);
        return jwt.generate(email);
    }

    public String login(String email, String password) {
        AppUser user = repo.findByEmail(email).orElseThrow();

        if (!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Bad credentials");

        return jwt.generate(email);
    }
}
