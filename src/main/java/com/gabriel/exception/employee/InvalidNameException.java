package com.gabriel.exception.employee;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super("Você deve informar o nome e o sobrenome!");
    }
}
