package com.abigail.libraryapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundException(UserNotFoundException except, WebRequest request){
        ErrorMessage ErrorDetails = new ErrorMessage(new Date(), except.getMessage(), request.getDescription(false));
        return  new ResponseEntity<>(ErrorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?>globeExceptionHandler(Exception except, WebRequest request){
        ErrorMessage errorDetails = new ErrorMessage(new Date(), except.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    protected ResponseEntity<Object>handleMethodValidationException(MethodValidationException except, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest request){
        ErrorMessage errorDetails = new ErrorMessage(new Date(), "Validation Failed",
                except.getAllValidationResults().toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}

