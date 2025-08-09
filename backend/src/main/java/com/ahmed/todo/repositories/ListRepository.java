package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.list.ReadListDto;
import com.ahmed.todo.entities.List;
import com.ahmed.todo.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Transactional
public interface ListRepository extends CrudRepository<List, Long> {
  boolean existsByIdAndUserId(Long id, Long userId);
  Optional<List> findByIdAndUserId(Long id, Long userId);
  java.util.List<List> findAllByUserId(Long userId);
  void deleteByIdAndUserId(Long id, Long userId);
}
