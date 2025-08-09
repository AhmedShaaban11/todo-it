package com.ahmed.todo.controllers;

import com.ahmed.todo.dtos.task.CompleteTaskDto;
import com.ahmed.todo.dtos.task.CreateTaskDto;
import com.ahmed.todo.dtos.task.ReadTaskDto;
import com.ahmed.todo.dtos.task.UpdateTaskDto;
import com.ahmed.todo.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping("/create")
  public void create(@RequestBody @Valid CreateTaskDto dto) {
    taskService.create(dto);
  }

  @GetMapping("/read")
  public Optional<ReadTaskDto> read(@RequestParam @NotNull @Positive Long id) {
    return taskService.read(id);
  }

  @GetMapping("/readAll")
  public List<ReadTaskDto> readAll(@RequestParam @NotNull @Positive Long listId) {
    return taskService.readAll(listId);
  }

  @PutMapping("/update")
  public void update(@RequestBody @Valid UpdateTaskDto dto) {
    taskService.update(dto);
  }

  @PutMapping("/complete")
  public void complete(@RequestBody @Valid CompleteTaskDto dto) {
    taskService.complete(dto);
  }

  @DeleteMapping("/delete")
  public void delete(@RequestParam @NotNull @Positive Long id) {
    taskService.delete(id);
  }
}
