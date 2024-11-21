package com.ahmed.todo.services;

import com.ahmed.todo.dtos.task.CompleteTaskDto;
import com.ahmed.todo.dtos.task.CreateTaskDto;
import com.ahmed.todo.dtos.task.ReadTaskDto;
import com.ahmed.todo.dtos.task.UpdateTaskDto;
import com.ahmed.todo.entities.Task;
import com.ahmed.todo.repositories.ListRepository;
import com.ahmed.todo.repositories.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final ListRepository listRepository;
  private final AccessService accessService;

  public TaskService(TaskRepository taskRepository, ListRepository listRepository, AccessService accessService) {
    this.taskRepository = taskRepository;
    this.listRepository = listRepository;
    this.accessService = accessService;
  }

  public void create(CreateTaskDto dto) {
    accessService.extractLoggedUserId().ifPresent(userId -> {
      boolean isListExist = listRepository.existsByIdAndUserId(dto.listId(), userId);
      if (isListExist) {
        taskRepository.save(dto.toTask());
      }
    });
  }

  public Optional<ReadTaskDto> read(Long id) {
    return accessService.extractLoggedUserId()
        .flatMap(userId -> taskRepository.findByIdAndUserId(id, userId));
  }

  public List<ReadTaskDto> readAll(Long listId) {
    return accessService.extractLoggedUserId()
        .map(userId -> taskRepository.findAllByListIdAndUserId(listId, userId))
        .orElseGet(List::of);
  }

  public void update(UpdateTaskDto dto) {
    accessService.extractLoggedUserId()
        .ifPresent(userId -> taskRepository.updateByUpdateTaskDtoAndUserId(dto, userId));
  }

  public void complete(CompleteTaskDto dto) {
    accessService.extractLoggedUserId()
        .ifPresent(userId -> taskRepository.completeByCompleteTaskDtoAndUserId(dto, userId));
  }

  public void delete(Long id) {
    accessService.extractLoggedUserId()
        .ifPresent(userId -> taskRepository.deleteByIdAndUserId(id, userId));
  }
}
