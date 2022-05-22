package com.example.priceservice.controller;


import com.example.priceservice.dto.request.ChangePriceRequest;
import com.example.priceservice.dto.response.PriceGenericResponse;
import com.example.priceservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.math.BigDecimal;

@CrossOrigin
@Validated
@Log4j2
@RequestScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/price")
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/{name}")
    public ResponseEntity<BigDecimal> getPriceProduct(@PathVariable("name") String name){
        log.info("Get price for this product -> " + name);
        return new ResponseEntity<>(priceService.getPriceForProduct(name), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PriceGenericResponse> changePriceProduct(@RequestBody @Valid ChangePriceRequest request){
        log.info("Price change request process start. -> " +  request.getName());
        return new ResponseEntity<>(priceService.changePriceProduct(request), HttpStatus.ACCEPTED);
    }


}
