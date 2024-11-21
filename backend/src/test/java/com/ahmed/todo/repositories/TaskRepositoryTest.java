package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.task.CreateTaskDto;
import com.ahmed.todo.dtos.task.ReadTaskDto;
import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.Task;
import com.ahmed.todo.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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

  private Long USER_ID;
  private Long LIST_ID;

  @BeforeEach
  public void setUp() {
    USER_ID = userRepository.save(new User("User", "user@gmail.com", "password")).getId();
    LIST_ID = listRepository.save(new List("List", USER_ID)).getId();
  }

  @AfterEach
  public void tearDown() {
    taskRepository.deleteAll();
    listRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  public void findByIdAndUserIdTestFound() {
    // given
    Task task = taskRepository.save(new Task("Task 1", "Description 1", false, LIST_ID));
    // when
    Optional<ReadTaskDto> opt = taskRepository.findByIdAndUserId(task.getId(), USER_ID);
    // then
    Assertions.assertThat(opt).isNotEmpty();
  }

  @Test
  public void findByIdAndUserIdTestUserNotFound() {
    // given
    Task task = taskRepository.save(new Task("Task 1", "Description 1", false, LIST_ID));
    // when
    Optional<ReadTaskDto> opt = taskRepository.findByIdAndUserId(task.getId(), USER_ID + 1);
    // then
    Assertions.assertThat(opt).isEmpty();
  }


  @Test
  public void findByIdAndUserIdTestTaskNotFound() {
    // when
    Optional<ReadTaskDto> opt = taskRepository.findByIdAndUserId(1L, USER_ID);
    // then
    Assertions.assertThat(opt).isEmpty();
  }


  @Test
  public void findByIdAndUserIdTestAllNotFound() {
    // given
    // when
    Optional<ReadTaskDto> opt = taskRepository.findByIdAndUserId(1L, USER_ID + 1);
    // then
    Assertions.assertThat(opt).isEmpty();
  }

  @Test
  public void findAllByListIdAndUserIdTestFound() {
    // given
    taskRepository.save(new Task("Task 1", "Description 1", false, LIST_ID));
    taskRepository.save(new Task("Task 2", "Description 2", true, LIST_ID));
    taskRepository.save(new Task("Task 3", "Description 3", false, LIST_ID));
    // when
    var tasks = taskRepository.findAllByListIdAndUserId(LIST_ID, USER_ID);
    // then
    Assertions.assertThat(tasks).hasSize(3);
  }
}
