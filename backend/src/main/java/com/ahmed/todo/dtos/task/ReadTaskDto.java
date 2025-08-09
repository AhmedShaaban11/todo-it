package com.ahmed.todo.dtos.task;

import com.ahmed.todo.entities.Task;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReadTaskDto(
    @NotNull @Positive Long id,
    @NotEmpty String title,
    String description,
    @NotNull Boolean isCompleted
) {
  public ReadTaskDto(Task task) {
    this(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
  }
}
