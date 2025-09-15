package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.UserDto;
import org.optimalwaytechtest.application.mappers.UserDtoMapper;
import org.optimalwaytechtest.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUsersUseCase {

    private final UserRepositoryPort userRepository;
    private final UserDtoMapper mapper;

    public ListUsersUseCase(UserRepositoryPort userRepository, UserDtoMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<UserDto> handle() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
