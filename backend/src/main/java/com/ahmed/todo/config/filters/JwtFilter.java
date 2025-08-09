package com.ahmed.todo.config.filters;

import com.ahmed.todo.components.SecurityUser;
import com.ahmed.todo.services.AccessService;
import com.ahmed.todo.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Configuration
public class JwtFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
    authorizeCookie(request);
    filterChain.doFilter(request, response);
  }

  public void authorizeCookie(@NonNull HttpServletRequest request) {
    if (request.getCookies() == null) { return; }
    String token = Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals(AccessService.ACCESS_TOKEN_COOKIE_NAME))
        .findFirst()
        .map(Cookie::getValue)
        .orElseGet(String::new);
    if (token.isEmpty()) { return; }
    String username = jwtService.verifyToken(token);
    if (username == null || username.isEmpty()) { return; }
    SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(username);
    if (user == null || SecurityContextHolder.getContext().getAuthentication() != null) { return; }
    var authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), user.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
