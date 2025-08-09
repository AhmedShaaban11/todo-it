package com.ahmed.todo.controllers;

import com.ahmed.todo.dtos.list.CreateListDto;
import com.ahmed.todo.dtos.list.ReadListDto;
import com.ahmed.todo.dtos.list.UpdateListDto;
import com.ahmed.todo.services.ListService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/list")
public class ListController {
  private final ListService listService;

  public ListController(ListService listService) {
    this.listService = listService;
  }

  @PostMapping("/create")
  public void create(@RequestBody @Valid CreateListDto dto) {
    listService.create(dto);
  }

  @GetMapping("/read")
  public ReadListDto read(@RequestParam @NotNull @Positive @Valid Long id) {
    return listService.read(id)
        .orElseThrow(() -> new IllegalArgumentException("List not found"));
  }

  @GetMapping("/readAll")
  public List<ReadListDto> readAll() {
    return listService.readAll();
  }

  @PutMapping("/update")
  public void update(@RequestBody @Valid UpdateListDto dto) {
    listService.update(dto);
  }

  @DeleteMapping("/delete")
  public void delete(@RequestParam @NotNull @Positive @Valid Long id) {
    listService.delete(id);
  }
}
