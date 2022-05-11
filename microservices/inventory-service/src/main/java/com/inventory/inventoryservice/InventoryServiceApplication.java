package com.inventory.inventoryservice;

import com.inventory.inventoryservice.entity.Inventory;
import com.inventory.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {

			var inventory = new Inventory();
			inventory.setSkuCode("IPHONE_12_PLUS_YELLOW");
			inventory.setQuantity(100);
			inventoryRepository.save(inventory);

			var inventory2 = new Inventory();
			inventory2.setSkuCode("IPHONE_12_RED");
			inventory2.setQuantity(10);
			inventoryRepository.save(inventory2);
		};
	}

}
