package org.optimalwaytechtest.infrastructure.config.security;

import java.util.Collections;

import org.optimalwaytechtest.infrastructure.persistence.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userRepository;

    public CustomUserDetailsService(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getEmail(), user.getPasswordHash(), Collections.emptyList());
    }
}
