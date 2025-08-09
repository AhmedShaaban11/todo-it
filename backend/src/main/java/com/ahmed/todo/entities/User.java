package com.ahmed.todo.entities;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private java.util.List<List> lists;

  public User() {
    this(null, null, null, null, null);
  }

  public User(String name, String email, String password) {
    this(null, name, email, password, null);
  }

  public User(Long id, String name, String email, String password, java.util.List<List> lists) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.lists = lists;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
