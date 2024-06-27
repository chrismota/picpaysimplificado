package com.picpaysimplificado.infra;

import com.picpaysimplificado.dtos.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler{

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception) {
        ExceptionDto exceptionDTO = new ExceptionDto("Usuário já cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(RuntimeException exception) {
        ExceptionDto exceptionDTO = new ExceptionDto(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
