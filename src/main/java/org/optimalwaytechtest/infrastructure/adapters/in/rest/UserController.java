package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.optimalwaytechtest.application.contracts.UserDto;
import org.optimalwaytechtest.application.usecases.ListUsersUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Manage users")
public class UserController {

    private final ListUsersUseCase listUsers;
    private final UserWebMapper mapper;

    public UserController(ListUsersUseCase listUsers, UserWebMapper mapper) {
        this.listUsers = listUsers;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List users", description = "Returns all registered users")
    public List<UserResponse> list() {
        List<UserDto> users = listUsers.handle();
        return users.stream().map(mapper::toResponse).toList();
    }
}
