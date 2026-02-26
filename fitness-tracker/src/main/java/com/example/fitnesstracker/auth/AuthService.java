package com.example.fitnesstracker.auth;

import com.example.fitnesstracker.auth.exception.UserAlreadyExistsException;
import com.example.fitnesstracker.user.AppUser;
import com.example.fitnesstracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(String email, String password) {

        if (repo.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        repo.save(user);

        String token = jwt.generate(email);

        return new AuthResponse(token);
    }

    public AuthResponse login(String email, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return new AuthResponse(jwt.generate(email));
    }
}
