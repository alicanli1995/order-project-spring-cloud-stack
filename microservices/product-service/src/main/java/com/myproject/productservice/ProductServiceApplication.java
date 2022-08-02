package com.myproject.productservice;

import com.myproject.productservice.entity.Product;
import com.myproject.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            if (!productRepository.findAll().isEmpty())
                return;

            var product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setDescription("Iphone 12 Plus RED Color 64 GB");
            product.setName("IPHONE_12_PLUS_RED");
            productRepository.save(product);

            var product2 = new Product();
            product2.setId(UUID.randomUUID().toString());
            product2.setDescription("Iphone 12 Plus BLUE Color 64 GB");
            product2.setName("IPHONE_12_PLUS_BLUE");
            productRepository.save(product2);

            var product3 = new Product();
            product3.setId(UUID.randomUUID().toString());
            product3.setDescription("Iphone 12 Plus GREEN Color 64 GB");
            product3.setName("IPHONE_12_PLUS_GREEN");
            productRepository.save(product3);

            var product4 = new Product();
            product4.setId(UUID.randomUUID().toString());
            product4.setDescription("Iphone 12 Plus YELLOW Color 64 GB");
            product4.setName("IPHONE_12_PLUS_YELLOW");
            productRepository.save(product4);

        };
    }

}
