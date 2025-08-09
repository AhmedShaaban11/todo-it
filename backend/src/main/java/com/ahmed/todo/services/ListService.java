package com.ahmed.todo.services;

import com.ahmed.todo.components.LoggedUserExtractor;
import com.ahmed.todo.dtos.list.CreateListDto;
import com.ahmed.todo.dtos.list.ReadListDto;
import com.ahmed.todo.dtos.list.UpdateListDto;
import com.ahmed.todo.repositories.ListRepository;
import com.ahmed.todo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListService {
  private final UserRepository userRepository;
  private final ListRepository listRepository;
  private final LoggedUserExtractor loggedUserExtractor;

  public ListService(UserRepository userRepository, ListRepository listRepository, LoggedUserExtractor loggedUserExtractor) {
    this.userRepository = userRepository;
    this.listRepository = listRepository;
    this.loggedUserExtractor = loggedUserExtractor;
  }

  public void create(CreateListDto dto) {
    loggedUserExtractor.extractId()
        .flatMap(userRepository::findById)
        .ifPresent(user -> listRepository.save(dto.toList(user)));
  }

  public Optional<ReadListDto> read(Long id) {
    return loggedUserExtractor.extractId().map(userId -> listRepository.findByIdAndUserId(id, userId)
        .map(ReadListDto::new)
        .orElseThrow(() -> new IllegalArgumentException("List not found")));
  }

  public java.util.List<ReadListDto> readAll() {
    return loggedUserExtractor.extractId()
        .map(userId -> listRepository.findAllByUserId(userId).stream()
            .map(ReadListDto::new)
            .collect(Collectors.toList()))
        .orElseGet(java.util.List::of);
  }

  public void update(UpdateListDto dto) {
    loggedUserExtractor.extractId()
        .flatMap(userRepository::findById)
        .ifPresent(user -> {
          boolean isExist = listRepository.existsByIdAndUserId(dto.id(), user.getId());
          if (!isExist) {
            throw new IllegalArgumentException("List not found");
          }
          listRepository.save(dto.toList(user));
        });
  }

  public void delete(Long id) {
    loggedUserExtractor.extractId().ifPresent(userId -> {
      listRepository.deleteByIdAndUserId(id, userId);
    });
  }
}
