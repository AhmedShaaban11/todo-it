package com.ahmed.todo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;
  private String description;
  private boolean isCompleted;
  private Long listId;

  public Task() {
  }

  public Task(Long id, String title, String description, boolean isCompleted, Long listId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.isCompleted = isCompleted;
    this.listId = listId;
  }

  public Task(String title, Long listId) {
    this(null, title, null, false, listId);
  }

  public Task(String title, String description, boolean isCompleted, Long listId) {
    this(null, title, description, isCompleted, listId);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public Long getListId() {
    return listId;
  }
}
