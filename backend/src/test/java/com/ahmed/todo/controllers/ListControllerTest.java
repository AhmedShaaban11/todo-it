package com.ahmed.todo.controllers;

import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.User;
import com.ahmed.todo.repositories.ListRepository;
import com.ahmed.todo.repositories.UserRepository;
import com.ahmed.todo.services.ListService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ListControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ListService listService;
  @Autowired
  private ListRepository listRepository;
  @Autowired
  private UserRepository userRepository;

  private User user;
  private List list;

  @BeforeEach
  void setUp() {
    user = userRepository.save(new User("user", "user@gmail.com", "password"));
    list = listRepository.save(new List("list", user));
  }

  @Test
  public void deleteListTest() throws Exception {
    // Arrange
    String uri = "/api/v1/list?id=" + list.getId();
    mockMvc.perform(delete(uri))
        .andExpect(status().isOk())
        .andExpect(result -> {
          assert (!listRepository.existsById(list.getId()));
        });
    // Act

    // Assert
  }
}
