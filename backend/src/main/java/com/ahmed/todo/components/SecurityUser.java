package com.ahmed.todo.components;

import com.ahmed.todo.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {
  private final User user;

  public SecurityUser(User user) {
    this.user = user;
  }

  public Long getId() {
    return user.getId();
  }

  public String getName() {
    return user.getName();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  public String getEmail() {
    return user.getEmail();
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

}
