package com.ahmed.todo.dtos.list;

import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.User;
import jakarta.validation.constraints.NotEmpty;

public record CreateListDto(
    @NotEmpty String title
) {
  public List toList(User user) {
    return new List(title, user);
  }
}
