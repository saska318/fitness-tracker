package com.example.fitnesstracker.user;

import com.example.fitnesstracker.auth.AppUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUser user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new AppUserPrincipal(user);
    }
}