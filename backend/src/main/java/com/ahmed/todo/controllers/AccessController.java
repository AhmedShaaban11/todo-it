package com.ahmed.todo.controllers;

import com.ahmed.todo.dtos.user.LoginUserDto;
import com.ahmed.todo.dtos.user.ProfileUserDto;
import com.ahmed.todo.dtos.user.RegisterUserDto;
import com.ahmed.todo.services.AccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/access")
public class AccessController {
  private final AccessService accessService;

  public AccessController(AccessService accessService) {
    this.accessService = accessService;
  }

  @GetMapping("/csrf")
  public void csrf() {  }

  @GetMapping("/isLogged")
  public ResponseEntity<Boolean> isLogged(HttpServletRequest request) {
    return ResponseEntity.ok(accessService.isLogged(request));
  }

  @GetMapping("/logout")
  public void logout(HttpServletResponse response) {
    accessService.logout(response);
  }

  @PostMapping("/login")
  public ResponseEntity<Boolean> login(@RequestBody @Valid LoginUserDto dto, HttpServletResponse response) {
    return ResponseEntity.ok(accessService.login(dto, response));
  }

  @PostMapping("/register")
  public boolean register(@RequestBody @Valid RegisterUserDto dto, HttpServletResponse response) {
    return accessService.register(dto, response);
  }

  @GetMapping("/profile")
  public Optional<ProfileUserDto> profile() {
    return accessService.profile();
  }
}
