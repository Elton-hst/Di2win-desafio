package com.di2win.config;

import com.di2win.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExeptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity usuarioCadastrado(DataIntegrityViolationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("usuario já cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeralExceptions(EntityNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "400");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
