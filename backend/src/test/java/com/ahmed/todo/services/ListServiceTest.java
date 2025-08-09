package com.ahmed.todo.services;

import com.ahmed.todo.components.LoggedUserExtractor;
import com.ahmed.todo.dtos.list.CreateListDto;
import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.User;
import com.ahmed.todo.repositories.ListRepository;
import com.ahmed.todo.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListServiceTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private ListRepository listRepository;

  @Mock
  private LoggedUserExtractor loggedUserExtractor;

  @InjectMocks
  private ListService listService;

  private Long LOGGED_USER_ID = 1L;
  private Long LIST_ID = 1L;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(loggedUserExtractor.extractId()).thenReturn(Optional.of(LOGGED_USER_ID));
    when(userRepository.findById(LOGGED_USER_ID)).thenReturn(Optional.of(new User()));
  }

  @Test
  public void deleteTestSuccess() {
    // Arrange
    // Act
    listService.create(new CreateListDto("list"));
    var opt1 = listService.read(LIST_ID);
    listService.delete(LIST_ID);
    var opt2 = listService.read(LIST_ID);
    // Assert
    verify(listRepository).deleteByIdAndUserId(LIST_ID, LOGGED_USER_ID);
    Assertions.assertThat(opt1.isEmpty()).isFalse();
    Assertions.assertThat(opt2.isEmpty()).isTrue();
  }
}
