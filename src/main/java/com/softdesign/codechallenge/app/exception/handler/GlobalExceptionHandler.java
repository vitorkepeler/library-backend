package com.softdesign.codechallenge.app.exception.handler;

import com.softdesign.codechallenge.domain.exception.BookIsRentedException;
import com.softdesign.codechallenge.domain.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
               "Something went wrong",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleCommon404Exception(BookNotFoundException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
               exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookIsRentedException.class)
    public ResponseEntity<String> handleCommon404Exception(BookIsRentedException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidatorsException(MethodArgumentNotValidException exception) {
        StringBuilder fields = new StringBuilder();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();

            fields.append(field).append(": ").append(errorMessage).append("\n");
        });

        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                fields.toString(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

}