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
            if (!priceRepository.findAll().isEmpty())
                return;

            var price = new Price();
            price.setName("IPHONE_12_PLUS_YELLOW");
            price.setPriceProduct(BigDecimal.valueOf(500));
            priceRepository.save(price);

            var price2 = new Price();
            price2.setName("IPHONE_12_PLUS_GREEN");
            price2.setPriceProduct(BigDecimal.valueOf(600));
            priceRepository.save(price2);

            var price3 = new Price();
            price3.setName("IPHONE_12_PLUS_BLUE");
            price3.setPriceProduct(BigDecimal.valueOf(756));
            priceRepository.save(price3);

            var price4 = new Price();
            price4.setName("IPHONE_12_PLUS_RED");
            price4.setPriceProduct(BigDecimal.valueOf(800));
            priceRepository.save(price4);


        };

    }
}
