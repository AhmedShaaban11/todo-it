package com.ahmed.todo.components;

import com.ahmed.todo.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoggedUserExtractor {
  public Optional<Long> extractId() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return Optional.empty();
    }
    return Optional.of((Long) auth.getPrincipal());
  }
}
