package com.libraryproject.library.resources.exceptions;

import com.libraryproject.library.entities.dto.FieldMessageDTO;
import com.libraryproject.library.entities.dto.StandardErrorDTO;
import com.libraryproject.library.entities.dto.ValidationErrorDTO;
import com.libraryproject.library.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.format.DateTimeParseException;


@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardErrorDTO err = new StandardErrorDTO(Instant.now(), status.value(), error, e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardErrorDTO> Database(DatabaseException e, HttpServletRequest request){
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardErrorDTO err = new StandardErrorDTO(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<StandardErrorDTO> Unprocessable(UnprocessableException e, HttpServletRequest request){
        String error = "This feature already exists.";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardErrorDTO err = new StandardErrorDTO(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        String error = "Invalid data.";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), error, "Invalid data.", request.getRequestURI());
        for(FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<StandardErrorDTO> dateTime(DateTimeException e, HttpServletRequest request){
        String error = "Incorrect date";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardErrorDTO err = new StandardErrorDTO(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<StandardErrorDTO> Forbidden(ForbiddenException e, HttpServletRequest request){
        String error = "Unauthorized user";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardErrorDTO err = new StandardErrorDTO(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
