package com.example.priceservice;

import com.example.priceservice.entity.Price;
import com.example.priceservice.repotisory.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableEurekaClient
public class PriceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner loadData(PriceRepository priceRepository) {

        return args -> {
            var price = new Price();
            price.setName("IPHONE_12_PLUS_YELLOW");
            price.setPriceProduct(BigDecimal.valueOf(500));
            priceRepository.save(price);

            var price2 = new Price();
            price2.setName("IPHONE_12_PLUS_GREEN");
            price2.setPriceProduct(BigDecimal.valueOf(5003));
            priceRepository.save(price2);
        };

    }
}
