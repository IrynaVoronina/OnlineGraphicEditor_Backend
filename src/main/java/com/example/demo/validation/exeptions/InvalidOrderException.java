package com.example.demo.validation.exeptions;

public class InvalidOrderException extends Exception {

    public InvalidOrderException(String message) {
        super(message);
    }
}
