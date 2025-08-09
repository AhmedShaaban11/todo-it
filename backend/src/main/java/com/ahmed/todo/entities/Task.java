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
  private Boolean isCompleted;

  @ManyToOne
  @JoinColumn(name = "list_id")
  private List list;

  public Task() {
    this(null, null, null, null, null);
  }

  public Task(String title, List list) {
    this(null, title, null, false, list);
  }

  public Task(String title, Boolean isCompleted, List list) {
    this(null, title, null, isCompleted, list);
  }

  public Task(Long id, String title, String description, Boolean isCompleted, List list) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.isCompleted = isCompleted;
    this.list = list;
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

  public Boolean isCompleted() {
    return isCompleted;
  }

  public List getList() {
    return list;
  }

  @Override
  public String toString() {
    return "Task(id=" + this.getId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", isCompleted=" + this.isCompleted() + ")";
  }
}
