package com.gabriel.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidNameAdvice {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageExceptionHandler> invalidNameHandler(RuntimeException exception) {
        MessageExceptionHandler error = new MessageExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }
}
