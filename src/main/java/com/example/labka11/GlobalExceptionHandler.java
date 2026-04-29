package com.example.labka11;

import com.example.labka11.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


// GlobalExceptionHandler.java — перехватчик ошибок, если у нас будет какая то ошибка он типа ловит

@ControllerAdvice
public class GlobalExceptionHandler {
// допустим тут ловит еррор если у нас нет айдишки он вернет ошибку еррор с текстом из сервиса
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handle(NotFoundException e){
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return ResponseEntity.status(404).body(error);
    }
    // а тут ловит на валидацию
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity.status(400).body(errors);
    }
}