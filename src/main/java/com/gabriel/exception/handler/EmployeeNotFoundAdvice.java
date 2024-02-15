package com.gabriel.exception.handler;

import com.gabriel.exception.employee.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class EmployeeNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> employeeNotFoundHandler(EmployeeNotFoundException exception) {
        MessageExceptionHandler error = new MessageExceptionHandler(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
