package com.example.demo.validation;

import com.example.demo.validation.exeptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ExistenceException.class)
    public ResponseEntity<ExceptionResponse> handleExistenceException(ExistenceException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOrderException(InvalidOrderException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(LayerException.class)
    public ResponseEntity<ExceptionResponse> handleLayerException(LayerException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(LocationException.class)
    public ResponseEntity<ExceptionResponse> handleLocationException(LocationException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UnsupportedFileFormatException.class)
    public ResponseEntity<ExceptionResponse> handleUnsupportedFileFormatException(UnsupportedFileFormatException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
