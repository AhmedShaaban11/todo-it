package com.ahmed.todo.dtos.task;

import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.Task;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTaskDto(
    @NotEmpty String title,
    @NotNull @Positive Long listId
) {
  public Task toTask(List list) {
    return new Task(title, list);
  }
}
