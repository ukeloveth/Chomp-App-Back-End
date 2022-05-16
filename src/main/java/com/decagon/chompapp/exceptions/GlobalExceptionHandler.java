package com.decagon.chompapp.exceptions;

import com.decagon.chompapp.dtos.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFoundException(ProductNotFoundException exception,
                                                                        WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(), webRequest.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                                        WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(), webRequest.getDescription(false));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);


    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ResponseEntity<ErrorDetails> handlePasswordConfirmationException(PasswordConfirmationException exception,
                                                                       WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(), webRequest.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(NoFavoriteException.class)
    public ResponseEntity<ErrorDetails> handleNoProductInFavoriteException(NoFavoriteException exception,
                                                                           WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(), webRequest.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException exception,
                                                                            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(), webRequest.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

}
