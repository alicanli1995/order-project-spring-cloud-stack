package com.myproject.productservice.config;


import com.myproject.productservice.dto.request.CreateProductDto;
import com.myproject.productservice.dto.response.ProductResponse;
import com.myproject.productservice.entity.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class AppConfig {
    private static final Converter<CreateProductDto, Product> PRODUCT_DTO_PRODUCT_CONVERTER =
            context -> Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(context.getSource().getName())
                    .description(context.getSource().getDescription())
                    .build();
    private static final Converter<Product, ProductResponse>  PRODUCT_TO_PRODUCT_RESPONSE_CONVERTER =
            context -> ProductResponse.builder()
                    .name(context.getSource().getName())
                    .description(context.getSource().getDescription())
                    .build();

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(PRODUCT_DTO_PRODUCT_CONVERTER,CreateProductDto.class,Product.class);
        modelMapper.addConverter(PRODUCT_TO_PRODUCT_RESPONSE_CONVERTER,Product.class,ProductResponse.class);
        return modelMapper;
    }

}
