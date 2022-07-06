package com.myproject.productservice.controller;

import com.myproject.productservice.dto.request.CreateProductDto;
import com.myproject.productservice.dto.response.ProductResponse;
import com.myproject.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Validated
@RequestScope
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody @Valid CreateProductDto createProductDto){
        productService.createProduct(createProductDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> retrieveAllProducts() throws InterruptedException {
        return ResponseEntity.ok(productService.retrieveAllProducts());
    }

    @GetMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> retrieveByName(@PathVariable("name") String name){
        return ResponseEntity.ok(productService.retrieveByName(name));
    }

}
