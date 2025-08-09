package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.task.CompleteTaskDto;
import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.Task;
import com.ahmed.todo.entities.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class TaskRepositoryTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ListRepository listRepository;
  @Autowired
  private TaskRepository taskRepository;

  private User user;
  private List list;

  @BeforeEach
  public void init() {
    user = userRepository.save(new User("User", "user@gmail.com", "password"));
    list = listRepository.save(new List("List", user));
  }

  private void completeByCompleteTaskDtoAndListUserIdTestFrom(boolean currentIsCompleted, boolean newIsCompleted) {
    Task task = taskRepository.save(new Task("Task", currentIsCompleted, list));
    System.out.println(task);
    var dto = new CompleteTaskDto(task.getId(), newIsCompleted);
    taskRepository.completeByCompleteTaskDtoAndListUserId(dto, user.getId());
    Optional<Task> updatedTaskOptional = taskRepository.findById(task.getId());
    Assertions.assertThat(updatedTaskOptional.isPresent()).isTrue();
    Assertions.assertThat(updatedTaskOptional.get().isCompleted()).isEqualTo(newIsCompleted);
  }

  @Test
  public void completeByCompleteTaskDtoAndListUserIdTestFromFalseToFalse() {
    completeByCompleteTaskDtoAndListUserIdTestFrom(false, false);
  }

  @Test
  public void completeByCompleteTaskDtoAndListUserIdTestFromFalseToTrue() {
    completeByCompleteTaskDtoAndListUserIdTestFrom(false, true);
  }

  @Test
  public void completeByCompleteTaskDtoAndListUserIdTestFromTrueToFalse() {
    completeByCompleteTaskDtoAndListUserIdTestFrom(true, false);
  }

  @Test
  public void completeByCompleteTaskDtoAndListUserIdTestFromTrueToTrue() {
    completeByCompleteTaskDtoAndListUserIdTestFrom(true, true);
  }

  @Test
  public void completeByCompleteTaskDtoAndListUserIdTestTaskNotFound() {
    Long wrongTaskId = 0L;
    var dto = new CompleteTaskDto(wrongTaskId, false);
    taskRepository.completeByCompleteTaskDtoAndListUserId(dto, user.getId());
    Optional<Task> updatedTaskOptional = taskRepository.findById(wrongTaskId);
    Assertions.assertThat(updatedTaskOptional.isPresent()).isFalse();
  }

  @Test
  public void completeByCompleteTaskDtoAndListUserIdTestTaskDoesntBelongToUser() {
    User anotherUser = userRepository.save(new User("AnotherUser", "another.user@gmail.com", "password"));
    List anotherList = listRepository.save(new List("List2", anotherUser));
    Task task = taskRepository.save(new Task("Task", false, anotherList));
    var dto = new CompleteTaskDto(task.getId(), true);
    taskRepository.completeByCompleteTaskDtoAndListUserId(dto, user.getId());
    Optional<Task> updatedTaskOptional = taskRepository.findById(task.getId());
    Assertions.assertThat(updatedTaskOptional.isPresent()).isTrue();
    Assertions.assertThat(updatedTaskOptional.get().isCompleted()).isFalse();
  }
}
