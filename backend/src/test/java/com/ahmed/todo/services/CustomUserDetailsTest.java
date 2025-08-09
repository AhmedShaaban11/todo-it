package com.ahmed.todo.services;

import com.ahmed.todo.components.SecurityUser;
import com.ahmed.todo.entities.User;
import com.ahmed.todo.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class CustomUserDetailsTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CustomUserDetailsService customUserDetailsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testLoadUserByUsernameOnSuccess() {
    // Arrange
    User mockUser = new User("user", "email@gmail.com", "password");
    SecurityUser mockSecurityUser = new SecurityUser(mockUser);
    when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
    // Act
    SecurityUser resultSecurityUser = customUserDetailsService.loadUserByUsername(mockUser.getEmail());
    // Assert
    Assertions.assertThat(resultSecurityUser.getUser()).isEqualTo(mockSecurityUser.getUser());
    verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
  }

  @Test
  public void testLoadUserByUsernameOnFailure() {
    // Arrange
    SecurityUser mockSecurityUser = new SecurityUser(new User("user", "email@gmail.com", "password"));
    when(userRepository.findByEmail(mockSecurityUser.getEmail())).thenReturn(Optional.empty());
    // Act
    SecurityUser resultSecurityUser = customUserDetailsService.loadUserByUsername(mockSecurityUser.getEmail());
    // Assert
    Assertions.assertThat(resultSecurityUser).isNull();
    verify(userRepository, times(1)).findByEmail(mockSecurityUser.getEmail());
  }
}
