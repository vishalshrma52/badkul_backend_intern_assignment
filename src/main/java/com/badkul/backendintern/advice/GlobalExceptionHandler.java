package com.badkul.backendintern.advice;

import com.badkul.backendintern.exception.*;
import com.badkul.backendintern.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiException> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        ApiException err = new ApiException();
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Not Found");
        err.setMessage(ex.getMessage());
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiException> handleConflict(ConflictException ex, HttpServletRequest req) {
        ApiException err = new ApiException();
        err.setStatus(HttpStatus.CONFLICT.value());
        err.setError("Conflict");
        err.setMessage(ex.getMessage());
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst().orElse(ex.getMessage());
        ApiException err = new ApiException();
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setError("Bad Request");
        err.setMessage(message);
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleOther(Exception ex, HttpServletRequest req) {
        ApiException err = new ApiException();
        err.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setError("Internal Server Error");
        err.setMessage(ex.getMessage());
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}

