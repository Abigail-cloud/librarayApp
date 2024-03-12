package com.abigail.libraryapp.exception;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Create a structure on how the error message
 * will look like
 * N>B not using the default error created by spring
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
        private Date timestamp;
        private String message;
        private String description;

}
