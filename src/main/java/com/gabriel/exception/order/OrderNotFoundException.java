package com.gabriel.exception.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Not found order with id " + id);
    }
}
