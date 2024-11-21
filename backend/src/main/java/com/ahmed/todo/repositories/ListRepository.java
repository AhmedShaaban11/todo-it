package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.list.ReadListDto;
import com.ahmed.todo.entities.List;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListRepository extends CrudRepository<List, Long> {
  boolean existsByIdAndUserId(Long id, Long userId);
  Optional<ReadListDto> findByIdAndUserId(Long id, Long userId);
  java.util.List<ReadListDto> findAllByUserId(Long id);
  @Transactional
  @Modifying
   void deleteByIdAndUserId(Long id, Long userId);
}
