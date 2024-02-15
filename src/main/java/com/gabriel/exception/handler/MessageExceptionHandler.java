package com.gabriel.exception.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Getter @Setter
public class MessageExceptionHandler {
    private String message;
    private Integer status;
    private Date timestamp;
    private Optional<Map<String,String>> errorsList;

    public MessageExceptionHandler(String message, Integer status) {
        this.message = message;
        this.status = status;
        this.timestamp = new Date();
    }

    public MessageExceptionHandler(Map<String,String> errors, Integer status) {
        this.message = "Dados de entrada incorretos";
        this.errorsList = Optional.ofNullable(errors);
        this.status = status;
        this.timestamp = new Date();
    }
}
