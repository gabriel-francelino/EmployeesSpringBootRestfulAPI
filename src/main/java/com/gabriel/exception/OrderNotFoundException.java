package com.gabriel.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id) {
        super("Not found order with id " + id);
    }
}
