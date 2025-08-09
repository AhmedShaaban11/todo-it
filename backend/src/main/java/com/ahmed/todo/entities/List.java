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

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "list", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private java.util.List<Task> tasks;

  public List() {
    this(null, null, null);
  }

  public List(String title, User user) {
    this(null, title, user);
  }

  public List(Long id, String title, User user) {
    this.id = id;
    this.title = title;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public User getUser() {
    return user;
  }
}
