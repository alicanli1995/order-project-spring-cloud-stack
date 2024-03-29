package com.myproject.productservice.service.impl;

import com.myproject.productservice.dto.request.CreateProductDto;
import com.myproject.productservice.dto.response.ProductResponse;
import com.myproject.productservice.entity.Product;
import com.myproject.productservice.exception.ProductAlreadyExist;
import com.myproject.productservice.repository.ProductRepository;
import com.myproject.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String debugId = "acecefaa-aae9-4382-8277-e320ef6b7687";
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Override
    @CacheEvict(cacheNames = "product-cache", allEntries = true)
    public void createProduct(CreateProductDto createProductDto){
        var product =  modelMapper.map(createProductDto, Product.class);
        if(Boolean.TRUE.equals(isExistProduct(product.getName()))){
            log.warn(String.format("This product has already save. Id is : -> %s" , product.getId()));
            throw new ProductAlreadyExist("This product has already save." ,  product.getId(),debugId );
        }
        var savingProduct = productRepository.insert(product);
        log.info(String.format("Product save was successfully id is : -> %s" , savingProduct.getId()));

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "product-cache")
    public List<ProductResponse> retrieveAllProducts() throws InterruptedException {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean retrieveByName(String name) {
      return productRepository.findAll().stream().anyMatch(product -> product.getName().equalsIgnoreCase(name));
    }

    public Boolean isExistProduct(String name){
        return productRepository.findAll().stream().anyMatch(x -> x.getName().equalsIgnoreCase(name));
    }
}
