package com.ahmed.todo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserDto(
    @NotEmpty @Email String email,
    @NotEmpty String password
) {
}
