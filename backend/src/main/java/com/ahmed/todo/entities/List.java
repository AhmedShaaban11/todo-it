package com.ahmed.todo.entities;

import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "lists")
public class List {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;
  private Long userId;

  public List() {
  }

  public List(Long id, String title, Long userId) {
    this.id = id;
    this.title = title;
    this.userId = userId;
  }

  public List(String title, Long userId) {
    this(null, title, userId);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Long getUserId() {
    return userId;
  }
}
