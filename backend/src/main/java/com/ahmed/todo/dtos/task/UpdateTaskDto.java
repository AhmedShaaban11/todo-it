package com.ahmed.todo.dtos.task;

import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.Task;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateTaskDto(
    @NotNull @Positive Long id,
    @NotEmpty String title,
    String description,
    @NotNull Boolean isCompleted
) {
  public Task toTask(List list) {
    return new Task(id, title, description, isCompleted, list);
  }
}
