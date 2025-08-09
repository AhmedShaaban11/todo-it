package com.ahmed.todo.dtos.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CompleteTaskDto(
    @NotNull @Positive Long id,
    @NotNull Boolean isCompleted
) {
}
