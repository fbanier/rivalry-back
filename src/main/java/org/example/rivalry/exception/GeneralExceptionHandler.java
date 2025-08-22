package org.example.rivalry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity Not Found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errorList = ex.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            return errorMap;
        }).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }

}
