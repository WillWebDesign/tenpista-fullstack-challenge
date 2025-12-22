package com.tenpista.backend.api.error;

import com.tenpista.backend.exception.BusinessException;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
    return ResponseEntity
        .badRequest()
        .body(new ErrorResponse(
            400,
            "Business Error",
            ex.getMessage(),
            LocalDateTime.now()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(e -> e.getField() + ": " + e.getDefaultMessage())
        .findFirst()
        .orElse("Validation error");

    return ResponseEntity
        .badRequest()
        .body(new ErrorResponse(
            400,
            "Validation Error",
            message,
            LocalDateTime.now()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(
            500,
            "Internal Server Error",
            "Unexpected error occurred",
            LocalDateTime.now()));
  }

  @ExceptionHandler({
      PropertyReferenceException.class,
      IllegalArgumentException.class
  })
  public ResponseEntity<ErrorResponse> handleInvalidPageable(Exception ex) {
    return ResponseEntity
        .badRequest()
        .body(new ErrorResponse(
            400,
            "Bad request",
            "Invalid pagination or sorting parameters",
            LocalDateTime.now()));
  }
}
