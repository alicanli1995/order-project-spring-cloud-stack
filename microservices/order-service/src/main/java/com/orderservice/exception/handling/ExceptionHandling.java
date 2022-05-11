package com.orderservice.exception.handling;

import com.orderservice.exception.NotProductFoundException;
import com.orderservice.exception.NotStockFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value = { NotStockFoundException.class, NotProductFoundException.class})
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {
        String bodyOfResponse = ex.getMessage() ;
        return new ResponseEntity(bodyOfResponse, HttpStatus.CONFLICT);
    }

}
