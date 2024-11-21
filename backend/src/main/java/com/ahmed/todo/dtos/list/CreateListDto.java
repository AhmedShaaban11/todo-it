package com.ahmed.todo.dtos.list;

import com.ahmed.todo.entities.List;
import jakarta.validation.constraints.NotEmpty;

public record CreateListDto(
    @NotEmpty String title
) {
  public List toList(Long userId) {
    return new List(title, userId);
  }
}
