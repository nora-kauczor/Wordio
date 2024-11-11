package org.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IdNotFoundException.class, LanguageNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        System.out.println(exception.getMessage());
       return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(VocabIsNotEditableException.class)
    public String handleVocabIsNotEditableException(Exception exception) {
        System.out.println(exception.getMessage());
        return exception.getMessage();
    }
}