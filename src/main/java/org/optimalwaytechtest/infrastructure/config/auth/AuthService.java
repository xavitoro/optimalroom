package org.optimalwaytechtest.infrastructure.config.auth;

import java.time.Instant;
import java.util.UUID;

import org.optimalwaytechtest.infrastructure.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserJpaRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }
        String hash = passwordEncoder.encode(rawPassword);
        UserEntity user = new UserEntity(UUID.randomUUID(), email, hash, Instant.now());
        userRepository.save(user);
    }
}
