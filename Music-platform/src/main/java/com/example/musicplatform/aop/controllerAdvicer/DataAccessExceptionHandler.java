package com.example.musicplatform.aop.controllerAdvicer;

import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.NotUniqueObjectException;
import com.example.musicplatform.message.ErrorMessage;
import org.jooq.exception.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DataAccessExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotUniqueObjectException.class)
    public ResponseEntity<ErrorMessage> handleNotUniqueObjectException(
            NotUniqueObjectException exception
    ) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorMessage> handleNotUniqueObjectException(
            DataAccessException exception
    ) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(
            NotFoundException exception
    ) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
