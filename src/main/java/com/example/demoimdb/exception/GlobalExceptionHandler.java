package com.example.demoimdb.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    public void handleConflict(HttpServletResponse response) throws IOException {
        response.sendError(403, "Bạn không có quyền truy cập trang này ");
    }

    @ExceptionHandler(ApiInputException.class)
    public ResponseEntity<String> handleApiInputException(ApiInputException ex) {
        log.warn(String.format("ApiInputException - Response: %s", ex.getError()));
        if (ex.getError().equals("PERMISSION_DENIED")) {
            return new ResponseEntity<>(ex.getError(), HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
        }
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleArgumentInputException(MethodArgumentNotValidException ex){
        String error =  ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException ex){
        String error =  ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("handleException - ", ex);
        return new ResponseEntity<>("UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
