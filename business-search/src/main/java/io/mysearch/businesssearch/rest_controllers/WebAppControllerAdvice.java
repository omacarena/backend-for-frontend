package io.mysearch.businesssearch.rest_controllers;

import io.mysearch.businesssearch.errors.BusinessNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Translates exceptions into error responses targeted towards client applications.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class WebAppControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<Exception> handleBusinessNotFound(BusinessNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
  }
}
