package com.ahmed.todo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
  private static final Logger logger = LoggerFactory.getLogger(GlobalRestControllerAdvice.class);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    logger.error(ex.getMessage(), ex);
    return ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.groupingBy(
            FieldError::getField,
            Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
        ));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleIllegalArgumentException(IllegalArgumentException ex) {
    logger.error(ex.getMessage(), ex);
    return ex.getMessage();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(IOException.class)
  public void handleIOException(IOException ex, final HttpServletResponse response) throws IOException {
    logger.error(ex.getMessage(), ex);
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RuntimeException.class)
  public void handleRuntimeException(RuntimeException ex, final HttpServletResponse response) throws IOException {
    logger.error(ex.getMessage(), ex);
    response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

}
