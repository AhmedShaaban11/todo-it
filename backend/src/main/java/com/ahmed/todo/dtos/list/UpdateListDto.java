package com.ahmed.todo.dtos.list;

import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateListDto(
    @NotNull @Positive Long id,
    @NotEmpty String title) {
  public List toList(User user) {
    return new List(id, title, user);
  }
}
