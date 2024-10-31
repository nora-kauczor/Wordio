package org.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IdNotFoundException.class, LanguageNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundExceptions(Exception exception) {
       ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        System.out.println(errorMessage.message());
       return errorMessage;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(VocabIsNotEditableException.class)
    public ErrorMessage handleVocabIsNotEditableException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        System.out.println(errorMessage.message());
        return errorMessage;
    }
}