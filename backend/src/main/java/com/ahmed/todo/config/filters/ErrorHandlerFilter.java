package com.ahmed.todo.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class ErrorHandlerFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerFilter.class);

  @Override
  public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
    try {
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      logger.error("Error processing request: {}", e.getMessage(), e);
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}
