package com.gabriel.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id) {
        super("Not found employee with id " + id);
    }
}
