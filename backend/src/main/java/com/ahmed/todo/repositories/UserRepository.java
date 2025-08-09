package com.ahmed.todo.repositories;

import com.ahmed.todo.dtos.list.ReadListDto;
import com.ahmed.todo.dtos.user.ProfileUserDto;
import com.ahmed.todo.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
