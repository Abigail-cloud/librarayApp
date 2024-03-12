package com.abigail.libraryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
        private static final Long serialVersionUID = 1L;
        public BookNotFoundException(String message){
            super(message);
        }

}
