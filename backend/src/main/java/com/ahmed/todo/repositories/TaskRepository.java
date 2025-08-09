package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.task.CompleteTaskDto;
import com.ahmed.todo.dtos.task.UpdateTaskDto;
import com.ahmed.todo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Optional<Task> findByIdAndListUserId(Long id, Long listUserId);
  java.util.List<Task> findAllByListIdAndListUserId(Long listId, Long listUserId);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("""
    UPDATE Task t
    SET t.isCompleted = :#{#dto.isCompleted}
    WHERE t.id = :#{#dto.id} AND t.list.user.id = :listUserId
  """)
  void completeByCompleteTaskDtoAndListUserId(CompleteTaskDto dto, Long listUserId);

//  @Transactional
@Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("""
    UPDATE Task t
    SET t.title = :#{#dto.title}, t.description = :#{#dto.description}, t.isCompleted = :#{#dto.isCompleted}
    WHERE t.id = :#{#dto.id} And t.list.user.id = :listUserId
  """)
  void updateByUpdateTaskDtoAndListUserId(UpdateTaskDto dto, Long listUserId);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  void deleteByIdAndListUserId(Long id, Long listUserId);
}
