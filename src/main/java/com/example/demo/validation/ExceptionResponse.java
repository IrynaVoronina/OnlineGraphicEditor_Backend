package com.example.demo.validation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExceptionResponse{

    private String errorMessage;
    private int errorCode;

}
