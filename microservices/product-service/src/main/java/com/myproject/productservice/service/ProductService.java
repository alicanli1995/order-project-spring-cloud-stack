package com.myproject.productservice.service;

import com.myproject.productservice.dto.request.CreateProductDto;
import com.myproject.productservice.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(CreateProductDto createProductDto);
    List<ProductResponse> retrieveAllProducts();

    Boolean retrieveByName(String name);
}
