 package com.leucine.exceptions;


 import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;

 @ControllerAdvice
 public class GlobalExceptionHandler {

     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException ex) {
         ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND);
         return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler(BadRequestException.class)
     public ResponseEntity<ApiException> handleBadRequestException(BadRequestException ex) {
         ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST);
         return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
     }

     // Add more exception handlers as needed
 }
