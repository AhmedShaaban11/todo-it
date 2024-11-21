package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.task.*;
import com.ahmed.todo.entities.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ahmed.todo.dtos.task.ReadTaskDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
  @Query("""
    SELECT new com.ahmed.todo.dtos.task.ReadTaskDto(t.id, t.title, t.description, t.isCompleted)
    FROM Task t
    JOIN List l ON t.listId = l.id
    JOIN User u ON l.userId = u.id
    WHERE t.id = :id AND u.id = :userId
  """)
  Optional<ReadTaskDto> findByIdAndUserId(Long id, Long userId);

  @Query("""
    SELECT new com.ahmed.todo.dtos.task.ReadTaskDto(t.id, t.title, t.description, t.isCompleted)
    FROM Task t
    JOIN List l ON t.listId = l.id
    JOIN User u ON l.userId = u.id
    WHERE l.id = :listId AND u.id = :userId
  """)
  List<ReadTaskDto> findAllByListIdAndUserId(Long listId, Long userId);

  @Transactional
  @Modifying
  @Query("""
    UPDATE Task t
    SET t.isCompleted = :#{#dto.isCompleted}
    WHERE t.id = :#{#dto.id}
    AND EXISTS (
      SELECT 1
      FROM List l
      JOIN User u ON l.userId = u.id
      WHERE t.listId = l.id AND u.id = :userId
    )
  """)
  void completeByCompleteTaskDtoAndUserId(CompleteTaskDto dto, Long userId);

  @Transactional
  @Modifying
  @Query("""
    UPDATE Task t
    SET t.title = :#{#dto.title}, t.description = :#{#dto.description}, t.isCompleted = :#{#dto.isCompleted}
    WHERE t.id = :#{#dto.id}
    AND EXISTS (
      SELECT 1
      FROM List l
      JOIN User u ON l.userId = u.id
      WHERE t.listId = l.id AND u.id = :userId
    )
  """)
  void updateByUpdateTaskDtoAndUserId(UpdateTaskDto dto, Long userId);

  @Transactional
  @Modifying
  @Query("""
    DELETE FROM Task t
    WHERE t.id = :id
    AND EXISTS (
      SELECT 1
      FROM List l
      JOIN User u ON l.userId = u.id
      WHERE t.listId = l.id AND u.id = :userId
    )
  """)
  void deleteByIdAndUserId(Long id, Long userId);
}
