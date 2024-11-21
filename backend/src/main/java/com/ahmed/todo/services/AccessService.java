package com.ahmed.todo.services;

import com.ahmed.todo.components.SecurityUser;
import com.ahmed.todo.dtos.user.LoginUserDto;
import com.ahmed.todo.dtos.user.ProfileUserDto;
import com.ahmed.todo.dtos.user.RegisterUserDto;
import com.ahmed.todo.entities.User;
import com.ahmed.todo.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AccessService {
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

  public AccessService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean isLogged(HttpServletRequest request) {
    if (request.getCookies() == null) { return false; }
    String token = Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_COOKIE_NAME))
        .findFirst()
        .map(Cookie::getValue)
        .orElseGet(String::new);
    if (token.isEmpty()) { return false; }
    return !jwtService.verifyToken(token).isEmpty();
  }

  public Optional<Long> extractLoggedUserId() {
    return SecurityContextHolder.getContext().getAuthentication() == null  ?
        Optional.empty() : Optional.of((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
  }

  private Cookie getAccessTokenCookie(String value, int expiry) {
    Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, value);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setAttribute("SameSite", "Strict");
    cookie.setPath("/api/v1");
    cookie.setMaxAge(expiry);
    return cookie;
  }

  public boolean login(LoginUserDto dto, HttpServletResponse response) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
    var authentication = authenticationManager.authenticate(authenticationToken);
    if (!authentication.isAuthenticated()) { return false; }
    String token = jwtService.createToken(dto.email());
    Cookie cookie = getAccessTokenCookie(token, JwtService.EXPIRY_SECONDS);
    response.addCookie(cookie);
    return true;
  }

  public void logout(HttpServletResponse response) {
    Cookie cookie = getAccessTokenCookie("", 0);
    response.addCookie(cookie);
  }

  public boolean register(RegisterUserDto dto, HttpServletResponse response) {
    if (userRepository.existsByEmail(dto.email())) { return false; }
    userRepository.save(dto.toUser(passwordEncoder.encode(dto.password())));
    return login(new LoginUserDto(dto.email(), dto.password()), response);
  }

  public Optional<ProfileUserDto> profile() {
    return extractLoggedUserId().flatMap(userRepository::findById)
        .map(ProfileUserDto::new);
  }
}
