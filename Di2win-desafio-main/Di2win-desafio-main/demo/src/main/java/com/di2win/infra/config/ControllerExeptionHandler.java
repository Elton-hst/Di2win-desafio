package com.di2win.infra.config;

import com.di2win.web.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExeptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity usuarioCadastrado(DataIntegrityViolationException exception) {
        ExceptionDto exceptionDTO = new ExceptionDto("usuario já cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeralExceptions(EntityNotFoundException exception) {
        ExceptionDto exceptionDTO = new ExceptionDto(exception.getMessage(), "400");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
