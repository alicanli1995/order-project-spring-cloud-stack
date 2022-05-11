package com.myproject.productservice.exception.handling;

import com.myproject.productservice.exception.ProductAlreadyExist;
import com.myproject.productservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value = { ProductAlreadyExist.class , ProductNotFoundException.class})
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {
        String bodyOfResponse = ex.getMessage() ;
        return new ResponseEntity(bodyOfResponse, HttpStatus.CONFLICT);
    }

}
