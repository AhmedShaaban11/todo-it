package com.ahmed.todo.services;

import com.ahmed.todo.components.LoggedUserExtractor;
import com.ahmed.todo.dtos.task.CompleteTaskDto;
import com.ahmed.todo.dtos.task.CreateTaskDto;
import com.ahmed.todo.dtos.task.ReadTaskDto;
import com.ahmed.todo.dtos.task.UpdateTaskDto;
import com.ahmed.todo.repositories.ListRepository;
import com.ahmed.todo.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {
  private final TaskRepository taskRepository;
  private final ListRepository listRepository;
  private final LoggedUserExtractor loggedUserExtractor;

  public TaskService(TaskRepository taskRepository, ListRepository listRepository, LoggedUserExtractor loggedUserExtractor) {
    this.taskRepository = taskRepository;
    this.listRepository = listRepository;
    this.loggedUserExtractor = loggedUserExtractor;
  }

  public void create(CreateTaskDto dto) {
    loggedUserExtractor.extractId()
        .flatMap(userId -> listRepository.findByIdAndUserId(dto.listId(), userId))
        .ifPresent(list -> taskRepository.save(dto.toTask(list)));
  }

  public Optional<ReadTaskDto> read(Long id) {
    return loggedUserExtractor.extractId()
        .flatMap(userId -> taskRepository.findByIdAndListUserId(id, userId))
        .map(ReadTaskDto::new);
  }

  public java.util.List<ReadTaskDto> readAll(Long listId) {
    return loggedUserExtractor.extractId()
        .map(userId -> taskRepository.findAllByListIdAndListUserId(listId, userId)
            .stream()
            .map(ReadTaskDto::new)
            .collect(Collectors.toList()))
        .orElseGet(java.util.List::of);
  }

  public void update(UpdateTaskDto dto) {
    loggedUserExtractor.extractId()
        .ifPresent(userId -> taskRepository.updateByUpdateTaskDtoAndListUserId(dto, userId));
  }

  public void complete(CompleteTaskDto dto) {
    loggedUserExtractor.extractId()
        .ifPresent(userId -> taskRepository.completeByCompleteTaskDtoAndListUserId(dto, userId));
  }

  public void delete(Long id) {
    loggedUserExtractor.extractId()
        .ifPresent(userId -> taskRepository.deleteByIdAndListUserId(id, userId));
  }
}
