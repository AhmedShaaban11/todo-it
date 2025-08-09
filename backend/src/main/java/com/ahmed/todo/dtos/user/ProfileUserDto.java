package com.ahmed.todo.dtos.user;

import com.ahmed.todo.components.SecurityUser;
import com.ahmed.todo.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProfileUserDto(
    @NotEmpty String name,
    @NotEmpty @Email String email
) {
  public ProfileUserDto(User user) {
    this(user.getName(), user.getEmail());
  }

  public ProfileUserDto(SecurityUser user) {
    this(user.getName(), user.getEmail());
  }
}
