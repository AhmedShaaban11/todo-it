package com.ahmed.todo.services;

import com.ahmed.todo.dtos.list.CreateListDto;
import com.ahmed.todo.dtos.list.ReadListDto;
import com.ahmed.todo.dtos.list.UpdateListDto;
import com.ahmed.todo.repositories.ListRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListService {
  private final ListRepository listRepository;
  private final AccessService accessService;

  public ListService(ListRepository listRepository, AccessService accessService) {
    this.listRepository = listRepository;
    this.accessService = accessService;
  }

  public void create(CreateListDto dto) {
    accessService.extractLoggedUserId()
        .ifPresent(userId -> listRepository.save(dto.toList(userId)));
  }

  public Optional<ReadListDto> read(Long id) {
    return accessService.extractLoggedUserId()
        .flatMap(userId -> listRepository.findByIdAndUserId(id, userId));
  }

  public java.util.List<ReadListDto> readAll() {
    return accessService.extractLoggedUserId()
        .map(listRepository::findAllByUserId)
        .orElseGet(java.util.List::of);
  }

  public void update(UpdateListDto dto) {
    accessService.extractLoggedUserId().ifPresent(userId -> {
      if (!listRepository.existsByIdAndUserId(dto.id(), userId)) {
        throw new IllegalArgumentException("List not found");
      }
      listRepository.save(dto.toList(userId));
    });
  }

  public void delete(Long id) {
    accessService.extractLoggedUserId().ifPresent(userId -> {
      listRepository.deleteByIdAndUserId(id, userId);
    });
  }
}
