package com.ahmed.todo.dtos.user;

import com.ahmed.todo.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDto(
    @NotEmpty String name,
    @NotEmpty @Email String email,
    @NotEmpty String password
) {
  public User toUser(String encryptedPassword) {
    return new User(name, email, encryptedPassword);
  }
}
