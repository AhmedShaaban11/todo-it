package com.ahmed.todo.services;

import com.ahmed.todo.components.SecurityUser;
import com.ahmed.todo.entities.User;
import com.ahmed.todo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public SecurityUser loadUserByUsername(String email) {
     User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    return new SecurityUser(user);
  }
}
