package com.inventory.inventoryservice.exception.handling;

import com.inventory.inventoryservice.exception.NotStockFoundException;
import com.inventory.inventoryservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value = { NotStockFoundException.class, ProductNotFoundException.class })
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {
        String bodyOfResponse = ex.getMessage() ;
        return new ResponseEntity(bodyOfResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}
